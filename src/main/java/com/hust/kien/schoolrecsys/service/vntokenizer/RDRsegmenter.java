package com.hust.kien.schoolrecsys.service.vntokenizer;

import java.io.*;
import java.util.*;

/**
 * @author DatQuocNguyen
 */
public class RDRsegmenter {

    private Node root;

    public RDRsegmenter(String modelPath, String vocalPath) {
        Vocabulary.init(vocalPath);
        this.constructTreeFromRulesFile(modelPath);
    }

    private void constructTreeFromRulesFile(String rulesFilePath) {
        InputStream inputStream = null;
        InputStreamReader inputStreamReader = null;
        BufferedReader buffer = null;
        try {
            inputStream = new FileInputStream(new File(rulesFilePath));
            inputStreamReader = new InputStreamReader(inputStream);
            buffer = new BufferedReader(inputStreamReader);
            String line;
            this.root = new Node(new FWObject(false), "NN", null, null, null, 0);
            Node currentNode = this.root;
            int currentDepth = 0;

            for (; (line = buffer.readLine()) != null; ) {
                int depth = 0;
                for (int i = 0; i <= 6; i++) { // Supposed that the maximum
                    // exception level is up to 6.
                    if (line.charAt(i) == '\t')
                        depth += 1;
                    else
                        break;
                }

                line = line.trim();
                if (line.length() == 0)
                    continue;

                if (line.contains("cc:"))
                    continue;

                FWObject condition = Utils.getCondition(line.split(" : ")[0].trim());
                String conclusion = Utils.getConcreteValue(line.split(" : ")[1].trim());

                Node node = new Node(condition, conclusion, null, null, null, depth);

                if (depth > currentDepth) {
                    currentNode.setExceptNode(node);
                } else if (depth == currentDepth) {
                    currentNode.setIfnotNode(node);
                } else {
                    while (currentNode.depth != depth)
                        currentNode = currentNode.fatherNode;
                    currentNode.setIfnotNode(node);
                }
                node.setFatherNode(currentNode);

                currentNode = node;
                currentDepth = depth;
            }
        } catch (Exception e) {

        } finally {
            try {
                buffer.close();
                inputStreamReader.close();
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private Node findFiredNode(FWObject object) {
        Node currentN = root;
        Node firedN = null;
        while (true) {
            if (currentN.satisfy(object)) {
                firedN = currentN;
                if (currentN.exceptNode == null) {
                    break;
                } else {
                    currentN = currentN.exceptNode;
                }
            } else {
                if (currentN.ifnotNode == null) {
                    break;
                } else {
                    currentN = currentN.ifnotNode;
                }
            }

        }

        return firedN;
    }

    // An initial word segmenter based on longest matching
    private List<WordTag> getInitialSegmentation(String sentence) {
        List<WordTag> wordtags = new ArrayList<>();

        for (String regex : Utils.NORMALIZER_KEYS)
            if (sentence.contains(regex))
                sentence = sentence.replaceAll(regex, Utils.NORMALIZER.get(regex));

        List<String> tokens = Arrays.asList(sentence.split("\\s+"));
        List<String> lowerTokens = Arrays.asList(sentence.toLowerCase().split("\\s+"));

        int senLength = tokens.size();
        int i = 0;
        while (i < senLength) {
            String token = tokens.get(i);
            if (token.chars().allMatch(Character::isLetter)) {

                if (Character.isLowerCase(token.charAt(0)) && (i + 1) < senLength) {
                    if (Character.isUpperCase(tokens.get(i + 1).charAt(0))) {
                        wordtags.add(new WordTag(token, "B"));
                        i++;
                        continue;
                    }
                }

                boolean isSingleSyllabel = true;
                for (int j = Math.min(i + 4, senLength); j > i + 1; j--) {
                    String word = String.join(" ", lowerTokens.subList(i, j));
                    if (Vocabulary.getInstance().getVnDict().contains(word)
                            || Vocabulary.getInstance().getVnLocations().contains(word)
                            || Vocabulary.getInstance().getCountryLNames().contains(word)) {

                        wordtags.add(new WordTag(token, "B"));
                        for (int k = i + 1; k < j; k++)
                            wordtags.add(new WordTag(tokens.get(k), "I"));

                        i = j - 1;

                        isSingleSyllabel = false;
                        break;
                    }
                }
                if (isSingleSyllabel) {
                    String lowercasedToken = lowerTokens.get(i);

                    if (Vocabulary.getInstance().getVnFirstSentWords().contains(lowercasedToken)
                            || Character.isLowerCase(token.charAt(0))
                            || token.chars().allMatch(Character::isUpperCase)
                            || Vocabulary.getInstance().getCountrySNames().contains(lowercasedToken)
                            || Vocabulary.getInstance().getWorldCompanies().contains(lowercasedToken)) {

                        wordtags.add(new WordTag(token, "B"));
                        i++;
                        continue;

                    }

                    // Capitalized
                    int ilower;
                    for (ilower = i + 1; ilower < Math.min(i + 4, senLength); ilower++) {
                        String ntoken = tokens.get(ilower);
                        if (Character.isLowerCase(ntoken.charAt(0))
                                || !ntoken.chars().allMatch(Character::isLetter)
                                || ntoken.equals("LBKT") || ntoken.equals("RBKT")) {
                            break;
                        }
                    }

                    if (ilower > i + 1) {
                        boolean isNotMiddleName = true;
                        if (Vocabulary.getInstance().getVnMiddleNames().contains(lowercasedToken) && (i >= 1)) {
                            String prevT = tokens.get(i - 1);
                            if (Character.isUpperCase(prevT.charAt(0))) {
                                if (Vocabulary.getInstance().getVnFamilyNames().contains(prevT.toLowerCase())) {
                                    wordtags.add(new WordTag(token, "I"));
                                    isNotMiddleName = false;
                                }
                            }
                        }
                        if (isNotMiddleName)
                            wordtags.add(new WordTag(token, "B"));
                        for (int k = i + 1; k < ilower; k++)
                            wordtags.add(new WordTag(tokens.get(k), "I"));

                        i = ilower - 1;
                    } else {
                        wordtags.add(new WordTag(token, "B"));
                    }
                }
            } else {
                wordtags.add(new WordTag(token, "B"));
            }

            i++;
        }

        return wordtags;

    }

    public Set<String> segmentTokenizer(String str) {
        Set<String> ls = new TreeSet<>();
        String line = str.trim();
        if (line.length() > 0) {
            List<WordTag> wordtags = this.getInitialSegmentation(line);
            StringBuilder sb = new StringBuilder();
            int size = wordtags.size();
            for (int i = 0; i < size; i++) {
                FWObject object = Utils.getObject(wordtags, size, i);
                Node firedNode = findFiredNode(object);
                if (firedNode.depth > 0) {
                    if (firedNode.conclusion.equals("B")) {
                        if (sb.length() > 0) {
                            ls.add(sb.toString());
                        }
                        sb = new StringBuilder(wordtags.get(i).form);
                    } else {
                        sb.append(" ").append(wordtags.get(i).form);
                    }
                } else {// Fired at root, return initialized tag
                    if (wordtags.get(i).tag.equals("B")) {
                        if (sb.length() > 0) {
                            ls.add(sb.toString());
                        }
                        sb = new StringBuilder(wordtags.get(i).form);
                    } else {
                        sb.append(" ").append(wordtags.get(i).form);
                    }
                }
            }
            if (sb.length() > 0) {
                ls.add(sb.toString());
            }
        }
        return ls;
    }
}

package com.ssafy12.tripchoice.algo.trie;

import java.util.List;

public interface Trie {
    void init(List<String> inputs);
    void insert(String input);
    List<String> searchAll(String keyword);
}

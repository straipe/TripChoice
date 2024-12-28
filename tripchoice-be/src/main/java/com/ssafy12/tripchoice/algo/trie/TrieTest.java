//package com.ssafy12.tripchoice.algo.trie;
//
//
//import com.ssafy12.tripchoice.algo.JamoUtils;
//
//import java.io.BufferedReader;
//import java.io.InputStreamReader;
//import java.util.List;
//
//public class TrieTest {
//    static Trie prefixTrie = PrefixTrie.getInstance();
//    static Trie chosungTrie = ChosungTrie.getInstance();
//    public static void main(String[] args) {
//
//        List<String> titiles = AttractionDaoImpl.getInstance().findAll().stream().map(dto -> dto.getTitle()).toList();
//        prefixTrie.init(titiles);
//        chosungTrie.init(titiles);
//
////        basicTrieTest(titiles);
////        chosungTireTest(titiles);
//
//        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
//        String input;
//        try {
//	        while(!(input = br.readLine()).equals("end")) {
//	        	searchAndPrintTest(input);
//	        }
//	        System.out.println("테스트 종료");
//        }catch(Exception e) {
//        	e.printStackTrace();
//        }
////        searchAndPrintTest("ㅇㄴ ㄱㅈ,");
////        searchAndPrintTest("ㅇㄴ");
////        searchAndPrintTest("서울");
////        searchAndPrintTest("ㅅㅇ ㅁㄷ");
////        searchAndPrintTest("ㅅㅇ ㅅㅊㄷ ㄱㅂㄱ");
////        searchAndPrintTest("경복궁");
//    }
//
//    private static void searchAndPrintTest(String keyword){
//        System.out.println("=====TEST START=====");
//        System.out.println("Search Keyword = " + keyword);
//        List<String> searchResult;
//        if(JamoUtils.isChosungString(keyword)){
//            searchResult = chosungTrie.searchAll(keyword);
//        }else {
//            searchResult = prefixTrie.searchAll(keyword);
//        }
//
//        System.out.println("Search Result = " + searchResult.stream().limit(10).toList());
//        System.out.println("=====TEST END=====");
//    }
//
//    private static void chosungTireTest(List<String> titiles) {
//        chosungTrie = ChosungTrie.getInstance();
//        chosungTrie.init(titiles);
//        searchAndPrint("ㅇㄴ");
//        searchAndPrint("ㅅㅇ ㅁㄷ");
//    }
//
//    private static void basicTrieTest(List<String> titiles) {
//        chosungTrie = PrefixTrie.getInstance();
//        chosungTrie.init(titiles);
////        trie.init(List.of("안녕하세요", "안녕테스트","안경입니다.","난녕하세요","가나다라마바사아자차카타파하","동해물과 백두산이 마르고 닳도록","하느님이 보우하사 우리나라 만세 무궁화 삼천리 화려 강산 대한 사람 대한으로 길이 보전 하세"));
////        searchAndPrint("안녕");
////        searchAndPrint("안ㄴ");
////        searchAndPrint("서울");
//        searchAndPrint("이니");
//    }
//
//    public static void searchAndPrint(String keyword){
//        List<String> searchResult = chosungTrie.searchAll(keyword);
//        System.out.println("searchResult = " + searchResult);
//    }
//}

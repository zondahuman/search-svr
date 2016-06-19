package com.abin.lee.search.service.lucene.test;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.*;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

import java.io.IOException;
import java.nio.file.Paths;

/**
 * Created with IntelliJ IDEA.
 * User: tinkpad
 * Date: 15-12-25
 * Time: 下午11:17
 * To change this template use File | Settings | File Templates.
 */
public class SearchLuceneTest {
    public static void main(String[] args) throws Exception {
          String result1 = queryByKeyWord("test");
//        FuzzyQuery fuzzyQuery = new FuzzyQuery();
    }

    public static String queryByKeyWord(String keyWord) throws Exception {
        Analyzer analyzer = new StandardAnalyzer();
        Directory dir = FSDirectory.open(Paths.get("D:/lucene/index"));
        IndexReader reader = DirectoryReader.open(dir);
        IndexSearcher indexSearcher = new IndexSearcher(reader);
//        Term term=new Term("city", "厦门");
//        Query match=new TermQuery(term);
        QueryParser parser = new QueryParser("info", analyzer);
        Query query = parser.parse(keyWord);
        TopDocs topDocs = indexSearcher.search(query, 1000);
        System.out.println("总共匹配多少个：" + topDocs.totalHits);
        ScoreDoc[] hits = topDocs.scoreDocs;
        // 应该与topDocs.totalHits相同
        System.out.println("多少条数据：" + hits.length);
        for (ScoreDoc scoreDoc : hits) {
            System.out.println("匹配得分：" + scoreDoc.score);
            System.out.println("文档索引ID：" + scoreDoc.doc);
            Document document = indexSearcher.doc(scoreDoc.doc);
            System.out.println(document.get("info"));
        }
        reader.close();
        dir.close();
        return null;
    }

    public static void queryAll() throws Exception {
        Analyzer analyzer = new StandardAnalyzer();
        Directory dir = FSDirectory.open(Paths.get("D:/lucene/index"));
        IndexReader reader = DirectoryReader.open(dir);
        IndexSearcher indexSearcher = new IndexSearcher(reader);
        QueryParser parser = new QueryParser("info", analyzer);
        Query query = parser.parse("lucene");
        TopDocs topDocs = indexSearcher.search(query, 1000);
        System.out.println("总共匹配多少个：" + topDocs.totalHits);
        ScoreDoc[] hits = topDocs.scoreDocs;
        // 应该与topDocs.totalHits相同
        System.out.println("多少条数据：" + hits.length);
        for (ScoreDoc scoreDoc : hits) {
            System.out.println("匹配得分：" + scoreDoc.score);
            System.out.println("文档索引ID：" + scoreDoc.doc);
            Document document = indexSearcher.doc(scoreDoc.doc);
            System.out.println(document.get("info"));
        }
        reader.close();
        dir.close();
    }
}

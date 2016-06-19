package com.abin.lee.search.service.lucene.test;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
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
public class CreateLuceneTest {
    public static void main(String[] args) throws IOException {
        Analyzer a = new StandardAnalyzer();
        Directory dir = FSDirectory.open(Paths.get("D:/lucene/index"));
        IndexWriterConfig iwc = new IndexWriterConfig(a);
        IndexWriter iw = new IndexWriter(dir, iwc);
        Document doc = new Document();
        doc.add(new TextField("info", "this is my first lucene test", Field.Store.YES));
        iw.addDocument(doc);
        iw.close();
        dir.close();
    }
}

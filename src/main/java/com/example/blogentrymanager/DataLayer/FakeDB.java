package com.example.blogentrymanager.DataLayer;

import com.example.blogentrymanager.grpc.BlogEntry;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class FakeDB {
    private static FakeDB single_instance = null;
    private List<BlogEntry> _blogEntries = new ArrayList<BlogEntry>();

    private FakeDB()
    {
        _blogEntries = new ArrayList<BlogEntry>();
        generateData();
    }

    private void generateData() {
        _blogEntries.add(
                BlogEntry.newBuilder().setId(1).setTitle("title1").setEntryText("blablabla1").build()
        );
        _blogEntries.add(
                BlogEntry.newBuilder().setId(2).setTitle("title2").setEntryText("blablabla2").build()
        );
        _blogEntries.add(
                BlogEntry.newBuilder().setId(3).setTitle("title3").setEntryText("blablabla3").build()
        );
    }

    public static FakeDB getInstance()
    {
        if (single_instance == null)
            single_instance = new FakeDB();


        return single_instance;
    }

    public List<BlogEntry> getEntries(){
        return _blogEntries;
    }

    public BlogEntry getEntryById(int id){
        try {
            return _blogEntries.stream().filter(entry -> entry.getId() == id).collect(Collectors.toList()).get(0);
        }
        catch (Exception ex){
            return null;
        }


    }


}

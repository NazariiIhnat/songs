package io;

import entity.Category;

import javax.xml.bind.annotation.adapters.XmlAdapter;

public class CategoryXMLAdapter extends XmlAdapter<String, Category> {
    @Override
    public Category unmarshal(String v) throws Exception {
        return Category.getCategoryByText(v);
    }

    @Override
    public String marshal(Category v) throws Exception {
        return v.toString();
    }
}

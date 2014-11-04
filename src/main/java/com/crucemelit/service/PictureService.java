package com.crucemelit.service;

public interface PictureService {

    void setPicture(byte[] bytesFromStream, long... id);

    String getPicture(long id);

}

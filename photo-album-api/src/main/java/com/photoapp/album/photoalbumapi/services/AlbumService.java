package com.photoapp.album.photoalbumapi.services;

import java.util.List;

import com.photoapp.album.photoalbumapi.model.AlbumEntity;

public interface AlbumService {

	List<AlbumEntity> getAlbums(String userId);

}

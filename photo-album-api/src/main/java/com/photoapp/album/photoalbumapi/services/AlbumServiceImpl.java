package com.photoapp.album.photoalbumapi.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.photoapp.album.photoalbumapi.model.AlbumEntity;

@Service
public class AlbumServiceImpl implements AlbumService {

	@Override
	public List<AlbumEntity> getAlbums(String userId) {

		List<AlbumEntity> response = new ArrayList<AlbumEntity>();
		AlbumEntity albumEntity = new AlbumEntity();
		albumEntity.setUserId(userId);
		albumEntity.setAlbumId("album1Id");
		albumEntity.setDescription("album 1 description");
		albumEntity.setId(1L);
		albumEntity.setName("album 1 name");

		AlbumEntity albumEntity2 = new AlbumEntity();
		albumEntity2.setUserId(userId);
		albumEntity2.setAlbumId("album2Id");
		albumEntity2.setDescription("album 2 description");
		albumEntity2.setId(2L);
		albumEntity2.setName("album 2 name");

		response.add(albumEntity);
		response.add(albumEntity2);

		return response;
	}

}

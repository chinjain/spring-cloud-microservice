package com.photoapp.album.photoalbumapi.controller;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.reflect.TypeToken;
import com.photoapp.album.photoalbumapi.model.AlbumEntity;
import com.photoapp.album.photoalbumapi.model.AlbumResponseModel;
import com.photoapp.album.photoalbumapi.services.AlbumService;

@RestController
@RequestMapping("/users/{id}")
public class AlbumController {

	@Autowired
	AlbumService albumService;

	@GetMapping("albums")
	public List<AlbumResponseModel> userAlbums(@PathVariable("id") String id) {

		List<AlbumResponseModel> responseModels = new ArrayList<AlbumResponseModel>();
		List<AlbumEntity> albumEntities = albumService.getAlbums(id);

		if (albumEntities == null || albumEntities.isEmpty()) {
			return responseModels;
		}

		Type type = new TypeToken<List<AlbumResponseModel>>() {
		}.getType();

		responseModels = new ModelMapper().map(albumEntities, type);

		return responseModels;

	}

}

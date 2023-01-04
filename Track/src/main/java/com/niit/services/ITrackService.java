package com.niit.services;

import com.niit.domain.Track;
import com.niit.exception.TrackAlreadyExist;
import com.niit.exception.TrackNotFoundException;

import java.util.List;

public interface ITrackService {
   public Track saveTrack(Track track) throws TrackAlreadyExist;
   public List<Track> getAllTrack() throws TrackNotFoundException;
   public String deleteTrack(int trackId) throws TrackNotFoundException;
   public List<Track> getAllTrackByTrackRating() throws TrackNotFoundException;
   public List<Track> getAllTrackByArtistName(String artistName) throws TrackNotFoundException;

}

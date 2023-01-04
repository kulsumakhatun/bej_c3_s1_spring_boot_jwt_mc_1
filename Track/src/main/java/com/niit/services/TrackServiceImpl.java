package com.niit.services;

import com.niit.domain.Track;
import com.niit.exception.TrackAlreadyExist;
import com.niit.exception.TrackNotFoundException;
import com.niit.repository.TrackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TrackServiceImpl implements ITrackService{

    TrackRepository trackRepository;

    @Autowired
    public TrackServiceImpl(TrackRepository trackRepository) {
        this.trackRepository = trackRepository;
    }

    @Override
    public Track saveTrack(Track track) throws TrackAlreadyExist {
        if(trackRepository.findById(track.getTrackId()).isEmpty()){
            return trackRepository.save(track);
        }
        throw new TrackAlreadyExist();
    }

    @Override
    public List<Track> getAllTrack() throws TrackNotFoundException{
        if(trackRepository.findAll().isEmpty()){
            throw new TrackNotFoundException();
        }
        return trackRepository.findAll();
    }

    @Override
    public String deleteTrack(int trackId) throws TrackNotFoundException {
        if(trackRepository.findById(trackId).isEmpty()){
            throw new TrackNotFoundException();
        }
            trackRepository.deleteById(trackId);
            return "Tracked deleted";

    }

    @Override
    public List<Track> getAllTrackByTrackRating() throws TrackNotFoundException {
        if(trackRepository.findALlTrackByRating().isEmpty()){
            throw new TrackNotFoundException();
        } else {
            return trackRepository.findALlTrackByRating();
        }
    }

    @Override
    public List<Track> getAllTrackByArtistName(String artistName) throws TrackNotFoundException {
        if (trackRepository.findAllTrackByTrackArtist(artistName).isEmpty()){
            throw new TrackNotFoundException();
        }
        return trackRepository.findAllTrackByTrackArtist(artistName);
    }
}

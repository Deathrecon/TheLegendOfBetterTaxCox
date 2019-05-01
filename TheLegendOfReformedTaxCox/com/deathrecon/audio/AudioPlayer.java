package com.deathrecon.audio;

import java.io.File;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class AudioPlayer {
	Clip clip;
	public void playSound(File Sound) {
		try {
			clip = AudioSystem.getClip();
			clip.open(AudioSystem.getAudioInputStream(Sound));
			clip.start();
		}catch(Exception e) {
			
		}
	}
	
	public void loopSound(File Sound) {
		try {
			clip = AudioSystem.getClip();
			clip.open(AudioSystem.getAudioInputStream(Sound));
			clip.loop(1000);
		}catch(Exception e) {
			
		}
	}
	public void stopSound(File Sound) {
		try {
			clip = AudioSystem.getClip();
			clip.stop();
		}catch(Exception e) {
			
		}
	}
}

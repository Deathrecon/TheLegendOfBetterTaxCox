package com.deathrecon.audio;

import java.io.File;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class AudioThemePlayer {
	Clip clip;
	
	public AudioThemePlayer(File Sound) {
		try {
			clip = AudioSystem.getClip();
			clip.open(AudioSystem.getAudioInputStream(Sound));
		}catch(Exception e) {
			
		}
	}
	
	public void playSound() {
		System.out.println("FRAME POSITION: " + clip.getFramePosition());
		clip.start();
	}
	
	public void loopSound() {
		clip.setFramePosition(0);
		clip.loop(Clip.LOOP_CONTINUOUSLY);

	}
	public void stopSound() {
		clip.stop();
	}
}

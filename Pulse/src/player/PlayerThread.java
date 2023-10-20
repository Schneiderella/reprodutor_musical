package player;

import javax.swing.JOptionPane;

import javazoom.jl.player.Player;

public class PlayerThread extends Thread {

	private Player player;

	public PlayerThread(Player player) {
		this.player = player;
	}

	public void run() {
		try {
			player.play();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void end() {
		try {
			player.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
package player;

import java.io.InputStream;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;

public class PlayerController {

	private final static int NOTSTARTED = 0;
	private final static int PLAYING = 1;
	private final static int PAUSED = 2;
	private final static int FINISHED = 3;

	private final Player player;

	private final Object playerLock = new Object();

	private int playerStatus = NOTSTARTED;

	public PlayerController(final InputStream inputStream) throws JavaLayerException {
		this.player = new Player(inputStream);
	}

	// Inicia a reprodução
	public void play() throws JavaLayerException {
		synchronized (playerLock) {
			switch (playerStatus) {
			case NOTSTARTED:
				final Runnable r = new Runnable() {
					public void run() {
						playInternal();
					}
				};
				final Thread t = new Thread(r);
				t.setDaemon(true);
				t.setPriority(Thread.MAX_PRIORITY);
				playerStatus = PLAYING;
				t.start();
				break;
			case PAUSED:
				resume();
				break;
			default:
				break;
			}
		}
	}

	// Pausa a reprodução
	public boolean pause() {
		synchronized (playerLock) {
			if (playerStatus == PLAYING) {
				playerStatus = PAUSED;
			}
			return playerStatus == PAUSED;
		}
	}

	// Retoma a reprodução
	public boolean resume() {
		synchronized (playerLock) {
			if (playerStatus == PAUSED) {
				playerStatus = PLAYING;
				playerLock.notifyAll();
			}
			return playerStatus == PLAYING;
		}
	}

	// Pausa a reprodução
	public void stop() {
		synchronized (playerLock) {
			playerStatus = FINISHED;
			playerLock.notifyAll();
		}
	}

	private void playInternal() {
		while (playerStatus != FINISHED) {
			try {
				if (!player.play(1)) {
					break;
				}
			} catch (final JavaLayerException e) {
				break;
			}
			// check if paused or terminated
			synchronized (playerLock) {
				while (playerStatus == PAUSED) {
					try {
						playerLock.wait();
					} catch (final InterruptedException e) {
						// terminate player
						break;
					}
				}
			}
		}
		close();
	}

	// Fecha o player
	public void close() {
		synchronized (playerLock) {
			playerStatus = FINISHED;
		}
		try {
			player.close();
		} catch (final Exception e) {

		}
	}

	// Verifica se a música realmente existe no computador do usuário
	public boolean musicaCarregadaComSucesso() {
		return this.player != null;
	}

}

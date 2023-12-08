package player;

import javax.swing.*;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;

import view.PrincipalGui;

@SuppressWarnings("serial")
public class MusicTimer extends JLabel {

	PrincipalGui p;
	private Timer timer;
	private long duration;
	private long startTime;
	private long elapsedTime; // Tempo decorrido até agora
	private long elapsedBeforePause; // Tempo decorrido antes da pausa
	private boolean isPaused;
	private SimpleDateFormat timeFormat;
	private JProgressBar progressBar;

	public MusicTimer(JProgressBar barraProgresso, PrincipalGui principalGui) {
		this.p = principalGui;
		this.progressBar = barraProgresso;
		timeFormat = new SimpleDateFormat("mm:ss");
		setText("00:00");
		setForeground(Color.LIGHT_GRAY);
		setFont(new Font("Arial", Font.BOLD, 12));
		isPaused = false;
		elapsedBeforePause = 0;

		timer = new Timer(1000, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (!isPaused) {
					long currentTime = System.currentTimeMillis();
					if (startTime == 0) {
						startTime = currentTime;
					}
					elapsedTime = elapsedBeforePause + currentTime - startTime;

					if (elapsedTime >= duration) {
						updateTime(duration);
						timer.stop();
						p.avancaMusica();
					} else {
						updateTime(elapsedTime);
					}
				}
			}
		});
	}

	// Inicia o contador
	public void start() {
		if (!timer.isRunning()) {
			timer.start();
		}
	}

	// Pausa o contador
	public void pause() {
		if (!isPaused) {
			isPaused = true;
			elapsedBeforePause = elapsedTime;
		}
	}

	// Retoma o contador
	public void resume() {
		if (isPaused) {
			isPaused = false;
			startTime = System.currentTimeMillis();
		}
	}

	// Interrmpe o contador
	public void stop() {
		timer.stop();
	}

	// Reseta o contador
	public void reset() {
		timer.stop();
		setText("00:00");
		elapsedTime = 0;
		elapsedBeforePause = 0;
		startTime = 0;
	}

	// Atualiza a barra de reprodução de acordo com o progresso
	private void updateTime(long elapsedTime) {
		String formattedTime = timeFormat.format(new Date(elapsedTime));
		setText(formattedTime);
		int progress = (int) ((double) elapsedTime / duration * 100);
		progressBar.setValue(progress);
	}

    // Atualiza o tempo de acordo com a duração da música
	public void setDuration(long durationMillis) {
		this.duration = durationMillis;
		updateTime(0);
	}
	
}

package player;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;

public class PlayerController {
	
	private String nomeArquivo;
	private Player player;
	private PlayerThread pt;
	
	public PlayerController(String nomeArquivo) {
		this.nomeArquivo = nomeArquivo;
	}
	
	public void tocar() {
		try {
			this.player = new Player(new FileInputStream(nomeArquivo));
			this.pt = new PlayerThread(this.player);
			this.pt.start();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (JavaLayerException e) {
			e.printStackTrace();
		}
	}
	
	public void parar() {
		this.pt.end();
	}
}

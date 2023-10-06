package player;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javax.swing.JOptionPane;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;

public class PlayerController {
	
	private String enderecoMusica;
	private Player player;
	private PlayerThread pt;
	
	public PlayerController(String enderecoMusica) {
		this.enderecoMusica = enderecoMusica;
	}
	
	public void tocar() {
		try {
			this.player = new Player(new FileInputStream(enderecoMusica));
			this.pt = new PlayerThread(this.player);
			this.pt.start();
		} catch (FileNotFoundException e) {
			//e.printStackTrace();
		} catch (JavaLayerException e) {
			e.printStackTrace();
		}
	}
	
	public void parar() {
		this.pt.end();
	}
	
	public boolean musicaCarregadaComSucesso() {
	    return this.player != null;
	}
	
	
}

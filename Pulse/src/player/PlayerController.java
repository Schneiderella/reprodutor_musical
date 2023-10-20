package player;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javax.swing.JOptionPane;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;

public class PlayerController {
<<<<<<< HEAD

	private String enderecoMusica;
	private Player player;
	private PlayerThread pt;

=======
	
	private String enderecoMusica;
	private Player player;
	private PlayerThread pt;
	
>>>>>>> f480da5e794f508c3d868441c7c0b1428c1fd5fe
	public PlayerController(String enderecoMusica) {
		this.enderecoMusica = enderecoMusica;
	}

	public void tocar() {
		try {
			this.player = new Player(new FileInputStream(enderecoMusica));
			this.pt = new PlayerThread(this.player);
			this.pt.start();
		} catch (FileNotFoundException e) {
<<<<<<< HEAD
			// e.printStackTrace();
=======
			//e.printStackTrace();
>>>>>>> f480da5e794f508c3d868441c7c0b1428c1fd5fe
		} catch (JavaLayerException e) {
			e.printStackTrace();
		}
	}

	public void parar() {
		this.pt.end();
	}
<<<<<<< HEAD

	public boolean musicaCarregadaComSucesso() {
		return this.player != null;
	}

=======
	
	public boolean musicaCarregadaComSucesso() {
	    return this.player != null;
	}
	
	
>>>>>>> f480da5e794f508c3d868441c7c0b1428c1fd5fe
}

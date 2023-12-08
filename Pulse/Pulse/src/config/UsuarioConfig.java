package config;

import java.util.prefs.Preferences;

public class UsuarioConfig {
	private static final String CODIGO_USUARIO_PREF = "codigoUsuario";

	// Salva o código do usuário que efetuou login
	public static void salvarCodigoUsuario(int codigoUsuario) {
		Preferences prefs = Preferences.userRoot().node("Pulse");
		prefs.putInt(CODIGO_USUARIO_PREF, codigoUsuario);
	}

	// Busca o código salvo
	public static int carregarCodigoUsuario() {
		Preferences prefs = Preferences.userRoot().node("Pulse");
		return prefs.getInt(CODIGO_USUARIO_PREF, -1);
	}

	// Limpa o código ao efetuar logoff
	public static void realizarLogoff() {
		Preferences prefs = Preferences.userRoot().node("Pulse");
		prefs.remove(CODIGO_USUARIO_PREF);
	}
}

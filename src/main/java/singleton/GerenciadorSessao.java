package autenticacao.sessao;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

// Singleton: instância única que controla todas as sessões ativas
public class GerenciadorSessao {

    private static GerenciadorSessao instancia;

    private final Map<String, LocalDateTime> sessoes = new HashMap<>();

    private GerenciadorSessao() {}

    public static synchronized GerenciadorSessao getInstance() {
        if (instancia == null) {
            instancia = new GerenciadorSessao();
        }
        return instancia;
    }

    public void registrarSessao(String usuario) {
        sessoes.put(usuario, LocalDateTime.now());
        System.out.println("[Sessão] Registrada para: " + usuario + " em " + sessoes.get(usuario));
    }

    public boolean temSessaoAtiva(String usuario) {
        return sessoes.containsKey(usuario);
    }

    public void encerrarSessao(String usuario) {
        sessoes.remove(usuario);
        System.out.println("[Sessão] Encerrada para: " + usuario);
    }

    public Map<String, LocalDateTime> getSessoesAtivas() {
        return Collections.unmodifiableMap(sessoes);
    }
}

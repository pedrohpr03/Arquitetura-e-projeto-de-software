package autenticacao;

public interface MecanismoAuth {
    boolean autenticar(String usuario);
    String getNome();
}
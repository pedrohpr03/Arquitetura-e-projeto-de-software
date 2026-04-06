package autenticacao;

// Factory Method: interface que define o contrato de criação de mecanismos
public interface CriadorMecanismo {
    MecanismoAuth criar();
}

package ModeloRest;

import lombok.Getter;
import lombok.Setter;

public class TokenDeRegistroFCM {

    @Getter
    @Setter
    private UsuarioREST usuario;
    @Getter
    @Setter
    private String tokenFCM;
}

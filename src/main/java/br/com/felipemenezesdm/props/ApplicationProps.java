package br.com.felipemenezesdm.props;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import java.util.HashMap;

@Component
@ConfigurationProperties(prefix = "app")
public class ApplicationProps {
    HashMap<String, String> exceptions;

    public HashMap<String, String> getExceptions() {
        return exceptions;
    }

    public void setExceptions(HashMap<String, String> exceptions) {
        this.exceptions = exceptions;
    }
}

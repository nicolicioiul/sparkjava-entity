package ro.enode.auth;

import ro.enode.api.Service;

import java.util.Map;

public class AuthService extends Service {
    /**
     * Create auth
     *
     * @param body String
     * @return bool
     */
    public boolean create(String body) {
        log.info("Auth request:" + body);
        Map map = transformer.toMap(body);
        return map.get("username").equals("jondoe") && map.get("password").equals("password");
    }

}

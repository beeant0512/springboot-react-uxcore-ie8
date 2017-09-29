package com.xstudio.config.encoder;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.StandardPasswordEncoder;

/**
 * sha-256 加密器
 *
 * @author xiaobiao
 * @version 1.0.0 on 2017/5/2.
 */
public class SHA256PasswordEncoder implements PasswordEncoderExtend {

    /*private static Logger LOGGER = LoggerFactory.getLogger(SHA256PasswordEncoder.class);

    @Override
    public String encode(CharSequence rawPassword) {
        return sha256encode(rawPassword);
    }

    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        String s = sha256encode(rawPassword);
        return encodedPassword.equals(s);
    }

    private String sha256encode(CharSequence rawPassword){
        MessageDigest md = null;
        StringBuffer sb = new StringBuffer();
        try {
            md = MessageDigest.getInstance("SHA-256");
            md.update(rawPassword.toString().getBytes());
            byte byteData[] = md.digest();

            for (byte byteDatum : byteData) {
                sb.append(Integer.toString((byteDatum & 0xff) + 0x100, 16).substring(1));
            }
        } catch (Exception e) {
            LOGGER.error("SHA-256加密失败", e);
        }

        return sb.toString();
    }*/

    private StandardPasswordEncoder standardPasswordEncoder = new StandardPasswordEncoder("SHA-256");

    @Override
    public String encode(CharSequence rawPassword) {
        return standardPasswordEncoder.encode(rawPassword);
    }

    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        return standardPasswordEncoder.matches(rawPassword, encodedPassword);
    }

    @Override
    public boolean matches(UserDetails userDetails, UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken) {
        return false;
    }
}

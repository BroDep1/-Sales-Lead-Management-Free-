package com.expohack.slm.passwordencoding;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class PasswordEncodingTest {

  @Test
  void testDemoPasswords() {
    int strength = 12;
    PasswordEncoder bcrypt = new BCryptPasswordEncoder(strength);
    System.out.println(bcrypt.encode("1312cc7b"));
    System.out.println(bcrypt.encode("d9e1872b"));
    System.out.println(bcrypt.encode("399166b3"));
    System.out.println(bcrypt.encode("a4b33713"));
    System.out.println(bcrypt.encode("72ba79f6"));
    System.out.println(bcrypt.encode("5a5bfe45"));
    assertTrue(bcrypt.matches("1312cc7b",
        "$2a$12$wvYIQLY.OFXILPhq5ZznL.GvEzFBy7xopC.pLvfc37ZzXs206fNsS"));
    assertTrue(bcrypt.matches("d9e1872b",
        "$2a$12$aWL6HPuEWbl6aUfBZ8zl5eh8hcDOqtLJOCQGekW9Mp8YmWSl6MGDa"));
    assertTrue(bcrypt.matches("399166b3",
        "$2a$12$3Z18mIt8JEoFfK4l4pbwMegoEY7CZ5ZE4E8nsCviLIe7kIbfvh5vy"));
    assertTrue(bcrypt.matches("a4b33713",
        "$2a$12$YzW9xNSOp5cnMi5ywktIuOKj5QsjOuY05v2NphR.7F5bSy.bp0pqi"));
    assertTrue(bcrypt.matches("72ba79f6",
        "$2a$12$zwzxVEuti.lt0nGAsq7IneAEaUXIIdFe70dUgCsmLZPigFlLH8Z86"));
    assertTrue(bcrypt.matches("5a5bfe45",
        "$2a$12$s4ys.WIlArxZqAFW64kOWOcTsJ8brv4hzs627nASAT0sDYNi0GMYO"));
  }
}

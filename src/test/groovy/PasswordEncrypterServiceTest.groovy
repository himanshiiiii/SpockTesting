

import spock.lang.Specification

class PasswordEncrypterServiceTest extends Specification {
    Boolean encyryptPassword(String pwd) {

        String encryptedPassword = passwordEncrypterService.encrypt(pwd)

        return encryptedPassword

    }





    def "Password should be encrypted" ( ) {

        given: "A user"

        User user = new User()


        and: "A password Encrypter Mock"

        def passwordEncrypterService = Mock(PasswordEncrypterService)

        passwordEncrypterService.encrypt(_ as String) >> "drowssap"

        user.passwordEncrypterService = passwordEncrypterService

        passwordEncrypterService.encrypt(_ as String) >> "drowssap"


        when: "encryptPassword is called"

        String encryptedPwd = user.encyryptPassword("password")


        then:

        encryptedPwd == "drowssap"

    }
}



import spock.lang.IgnoreRest
import spock.lang.Shared
import spock.lang.Specification
import spock.lang.Unroll

class TransactionSpec extends Specification {

    def "test"() {

    }

    def "validating Sell Method"() {
        setup:
        User user = new User()
        Product product = new Product()
        Product product1 = new Product(price: 300)

        User user1 = new User(balance: 5000)
        user1.purchasedProducts.add("product added")
        Transaction transaction = new Transaction()

        when:
        transaction.sell(product, user)
        then:
        thrown(SaleException)

        when:
        transaction.sell(product1, user1)

        then:
        user1.purchasedProducts.size() == 2


    }


    def "cancel method"() {
        given:

        Product product = new Product(name: 'p1', price: 200)
        User user = new User(isPrivellegedCustomer: true)
        Transaction transaction = new Transaction()

        transaction.calculateDiscount(product, user)
        def emailService = Mock(EmailService)
        transaction.emailService = emailService

        when:
        transaction.cancelSale(product, user)
        then:
        user.purchasedProducts.size() == 0
    }

    def "discount calculation"() {
        given:

        Product product = new Product(name: 'product1', price: 100, discountType: DiscountType.NONE)
        User user = new User(isPrivellegedCustomer: true)

        User user1 = new User(isPrivellegedCustomer: false)
        Transaction transaction = new Transaction()

        Product product1 = new Product(name: 'product1', price: 100, discountType: DiscountType.PRIVELLEGE_ONLY)

        Product product2 = new Product(name: 'product1', price: 100, discountType: DiscountType.ALL)



        when:
        BigDecimal discount = transaction.calculateDiscount(product, user)
        then:
        discount == 0


        when:
        BigDecimal discount1 = transaction.calculateDiscount(product1, user)
        then:
        discount1 == 30


        when:
        BigDecimal discount2 = transaction.calculateDiscount(product1, user1)
        then:
        discount2 == 10

        when:
        BigDecimal discount3 = transaction.calculateDiscount(product2, user1)
        then:
        discount2 == 10


    }
}

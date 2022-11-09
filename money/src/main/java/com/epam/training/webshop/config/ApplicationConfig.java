package com.epam.training.webshop.config;

import com.epam.training.webshop.cart.Cart;
import com.epam.training.webshop.cart.ShoppingCartService;
import com.epam.training.webshop.cart.impl.ShoppingCartServiceImpl;
import com.epam.training.webshop.gross.GrossPriceCalculator;
import com.epam.training.webshop.order.OrderRepository;
import com.epam.training.webshop.order.confirmation.OrderConfirmationService;
import com.epam.training.webshop.presentation.cli.CliInterpreter;
import com.epam.training.webshop.presentation.cli.command.CommandLineParser;
import com.epam.training.webshop.presentation.cli.command.impl.AddProductCommandLineParser;
import com.epam.training.webshop.presentation.cli.command.impl.ExitCommandLineParser;
import com.epam.training.webshop.presentation.cli.command.impl.OrderCommandLineParser;
import com.epam.training.webshop.product.ProductRepository;
import com.epam.training.webshop.product.impl.DummyProductRepository;
import com.epam.training.webshop.warehouse.WareHouse;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.util.CollectionUtils;

import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Configuration
//@ComponentScan("com.epam.training.webshop")
//@PropertySource(value = "classpath:application.properties", encoding = "UTF-8")
public class ApplicationConfig {


    @Bean
    public static Writer cliWriter() {
        return new OutputStreamWriter(System.out);
    }

    @Bean
    public static Reader cliReader() {
        return new InputStreamReader(System.in);
    }

    @Bean
    public static CliInterpreter cliInterpreter(final Reader cliReader,
                                                final Writer cliWriter,
                                                @Lazy final CommandLineParser commandLineParser) {
        CliInterpreter cliInterpreter = new CliInterpreter(cliReader, cliWriter);
        cliInterpreter.updateCommandLineParser(commandLineParser);
        return cliInterpreter;
    }

//    @Bean
//    public static CommandLineParser commandLineParser(final ShoppingCartService shoppingCartService,
//                                                      final ProductRepository productRepository,
//                                                      @Lazy final CliInterpreter cliInterpreter
//    ) {
//        final CommandLineParser commandLineParserChain = new ExitCommandLineParser(cliInterpreter);
//        commandLineParserChain.setSuccessor(new AddProductCommandLineParser(productRepository, shoppingCartService));
//        commandLineParserChain.setSuccessor(new OrderCommandLineParser(shoppingCartService));
//        return commandLineParserChain;
//    }

    @Bean
    public static CommandLineParser commandLineParser(
            final List<CommandLineParser> commandLineParserList) {
        LinkedList<CommandLineParser> commandLineParsers = new LinkedList<>(commandLineParserList);
        CommandLineParser firstParser = commandLineParsers.getFirst();
        CommandLineParser actual = commandLineParsers.removeFirst();
        while(!CollectionUtils.isEmpty(commandLineParsers)) {
            CommandLineParser nextParser = commandLineParsers.removeFirst();
            actual.setSuccessor(nextParser);
            actual = nextParser;
        }
        return firstParser;
    }

    @Bean
    public static ShoppingCartService cart(
            final Cart cart,
            final WareHouse wareHouse,
            final OrderConfirmationService orderConfirmationService,
            final OrderRepository orderRepository,
            final ProductRepository productRepository,
            @Qualifier("hungarianGrossPriceCalculator") final GrossPriceCalculator grossPriceCalculator
    ) {
        final ShoppingCartService shoppingCart = new ShoppingCartServiceImpl(cart, orderRepository, productRepository, grossPriceCalculator, new ArrayList<>());
        shoppingCart.subscribe(wareHouse);
        shoppingCart.subscribe(orderConfirmationService);
        return shoppingCart;
    }


    @Bean(initMethod = "initProducts")
    public static ProductRepository productRepository() {
        return new DummyProductRepository();
    }
}

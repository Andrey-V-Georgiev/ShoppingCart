package com.shopping_cart.config;

import com.shopping_cart.models.entities.Contact;
import com.shopping_cart.models.entities.Product;
import com.shopping_cart.repositories.ContactRepository;
import com.shopping_cart.repositories.ProductRepository;
import org.modelmapper.ModelMapper;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;


@Configuration
public class AppBeanConfiguration {

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public ApplicationRunner initializer(
            ProductRepository productRepository,
            ContactRepository contactRepository) {

        if (productRepository.count() == 0) {

             productRepository.saveAll(Arrays.asList(
                    new Product(
                            "GLOCK 17 Gen5",
                            "The new frame design of the GLOCK 17 Gen5 removed the finger grooves for more versatility but still allows to easily customize its grip by using the different back straps. A flared mag-well and a cutout at the front of the frame give the user more speed during reloading when fractions of a second matter. A reversible enlarged magazine catch, changeable at user level as well as the ambidextrous slide stop lever accommodate left and right-handed operators. The rifling and the crown of the barrel were slightly modified for increased precision.",
                            "https://cdnammoclub.ammoforsale.com/ammo-club/media/DSC08507-scaled.jpg",
                            BigDecimal.valueOf(911),
                            LocalDateTime.now()
                    ),
                    new Product(
                            "G19 Gen5 FS",
                            "The GLOCK 19 Gen5 pistol in 9mm Luger is ideal for a more versatile role due to its reduced dimensions. The new frame design without finger grooves still allows to instantly customize its grip to accommodate any hand size by mounting the different back straps. The reversible magazine catch and ambidextrous slide stop lever make it ideal for left and right-handed shooters. The rifling and the crown of the barrel were slightly modified for increased precision..",
                            "https://i.pinimg.com/originals/d8/3d/c7/d83dc74b151313c13fe94d1954c9f2d9.jpg",
                            BigDecimal.valueOf(729),
                            LocalDateTime.now()
                    ),
                    new Product(
                            "H&K VP9 Tactical OR",
                            "The new HK VP9 Tactical OR (Optics Ready) Pistol is the latest addition to the VP family of HK pistols that is both optics-ready and suppressor-ready. This pistol stands out with its 4.7 inch hammer-forged barrel with threaded muzzle and suppressor-height tritium sights. Providing a day or night lower 1/3 co-witness through the window of a red dot sight, the sights remain visible over the top of most suppressors. Also included is an optics cut that, when combined with one of five availble adapter plates, accepts most popular red dot sights. Suppressor and/or optics can be added with no modifications needed.",
                            "https://isteam.wsimg.com/ip/a7237bb7-0471-4ebe-82e6-c17ed6d9db95/ols/1055_original/:/rs=w:600,h:600",
                            BigDecimal.valueOf(999),
                            LocalDateTime.now()
                    ),
                    new Product(
                            "Sig Sauer P220 LEGION",
                            "The Sig Sauer® P220® Legion SAO Full-Size Semi-Auto Pistol features a stainless steel slide with high-visibility Sig Sauer Electro-Optics X-RAY day/night sights. Custom G-10 grips allow sure handling, while front cocking serrations pair with aggressive checkering on the front strap and under the trigger guard to ensure a solid grip when it matters. An X-Five undercut on the trigger guard lets you get a higher grip for better weapon control. Low-profile levers reduce the risk of snagging when you need to get your weapon out in a hurry. Steel guide rod adds weight to enhance stability. ",
                            "https://i2.wp.com/shootarillusions.com/wp-content/uploads/2021/04/pix679079443.jpg?fit=1000%2C750&ssl=1",
                            BigDecimal.valueOf(1329),
                            LocalDateTime.now()
                    ),
                     new Product(
                             "CZ Shadow 2 OR",
                             "A sport special intended especially for fans of target shooting with large-calibre pistols, as well as anyone who agrees with the pistol guru, Jeff Cooper, that a double action trigger mechanism is an ingenious solution for a non-existent problem. (Although where the CZ 75 is concerned, Cooper himself admitted that the solution was indeed perfect). The single action variant of the phenomenal CZ SHADOW 2 model features a target style trigger blade made of aluminium alloy anodized blue and a combat style hammer with a large, weight-reducing hole. The trigger pull weight is set at 13 to 15 N, it can be decreased with a special adjustment or by swapping the trigger spring (we highly recommend such adjustments be carried out by a gunsmith or a specialized workshop). In comparison with the single action mode of the SA/DA model, this exclusively SA trigger mechanism also features an excellent and significantly shorter trigger travel (achieved by setting the first stage travel) and reset.",
                             "https://content.osgnetworks.tv/gunsandammo/content/photos/CZ-Shadow-2-OR-770.jpg",
                             BigDecimal.valueOf(1349),
                             LocalDateTime.now()
                     ),
                     new Product(
                             "STI DVC-P DUO 9mm",
                             "The STI DVC DUO (Dawson Universal Optic) system is designed to work with the Leupold Delta Point Pro, Trijicon RMR, and most popular optics that use the same mounting platform. 25 years of evolution and refinement resulted in the Omni. Nothing comes close.5″ Recoil Compensation. DUO (Dawson Universal Optic) with Fiber Optic Fron",
                             "https://pistol-forum.com/attachment.php?attachmentid=53982&d=1589495813",
                             BigDecimal.valueOf(3999),
                             LocalDateTime.now()
                     )
            ));
        }

        if (contactRepository.count() == 0) {

             contactRepository.saveAll(Arrays.asList(
                    new Contact(
                            "Sofia",
                            "https://www.google.com/maps/embed?pb=!1m18!1m12!1m3!1d2932.3179396759338!2d23.329512315700562!3d42.69698822174912!2m3!1f0!2f0!3f0!3m2!1i1024!2i768!4f13.1!3m3!1m2!1s0x40aa8570f039ab5f%3A0x29f700e9fb9d4353!2sul.%20%22Moskovska%22%2033%2C%201000%20Sofia%20Center%2C%20Sofia!5e0!3m2!1sen!2sbg!4v1628771840855!5m2!1sen!2sbg",
                            "33 Moskovska Str. Sofia 1000",
                            "0888 888 881",
                            "shopping-cart-sofia@gmail.com",
                            "09:00 - 17:00",
                            "10:00 - 16:00"
                    ),
                    new Contact(
                            "Plovdiv",
                            "https://www.google.com/maps/embed?pb=!1m18!1m12!1m3!1d2958.3108304311527!2d24.746848415449495!3d42.14363487920201!2m3!1f0!2f0!3f0!3m2!1i1024!2i768!4f13.1!3m3!1m2!1s0x14acd1bac53d66b3%3A0xa3e24860cb8d7ba4!2spl.%20%22Stefan%20Stambolov%22%201%2C%204000%20Tsentar%2C%20Plovdiv!5e0!3m2!1sen!2sbg!4v1628787167519!5m2!1sen!2sbg",
                            "4000, pl.Stefan Stambolov 1",
                            "0888 888 882",
                            "shopping-cart-plovdiv@gmail.com",
                            "09:30 - 17:30",
                            "10:40 - 16:40"
                    ),
                    new Contact(
                            "Varna",
                            "https://www.google.com/maps/embed?pb=!1m18!1m12!1m3!1d2907.8014619994174!2d27.923320315712537!3d43.21365338875918!2m3!1f0!2f0!3f0!3m2!1i1024!2i768!4f13.1!3m3!1m2!1s0x0%3A0x958943ca7d072728!2z0J_QsNC80LXRgtC90LjQuiDQvdCwINCe0YHQvNC4INC_0LXRhdC-0YLQtdC9INC_0YDQuNC80L7RgNGB0LrQuCDQv9C-0LvQug!5e0!3m2!1sen!2sbg!4v1628772176805!5m2!1sen!2sbg",
                            "9000, Bulgaria Osmi Primorski Polk 43",
                            "0888 888 883",
                            "shopping-cart-varna@gmail.com",
                            "09:00 - 17:00",
                            "10:30 - 16:30"
                    ),
                    new Contact(
                            "Burgas",
                            "https://www.google.com/maps/embed?pb=!1m18!1m12!1m3!1d2941.927260424515!2d27.470166915695856!3d42.4930990346817!2m3!1f0!2f0!3f0!3m2!1i1024!2i768!4f13.1!3m3!1m2!1s0x40a694c73b5b063d%3A0xef53d6e5fa2881a4!2sul.%20%22Aleksandrovska%22%2026%2C%208000%20Burgas%20Center%2C%20Burgas!5e0!3m2!1sen!2sbg!4v1628772341245!5m2!1sen!2sbg",
                            "8000 Alexandrovska Street 26",
                            "0888 888 884",
                            "shopping-cart-burgas@gmail.com",
                            "09:40 - 17:40",
                            "11:00 - 16:00"
                    )
            ));
        }
        return null;
    }
}

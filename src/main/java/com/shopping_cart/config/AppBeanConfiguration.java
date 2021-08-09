package com.shopping_cart.config;

import com.shopping_cart.models.entities.Product;
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
    public ApplicationRunner initializer(ProductRepository repository) {

        if (repository.count() == 0) {
            return args -> repository.saveAll(Arrays.asList(
                    new Product(
                            "GLOCK 17 Gen5",
                            "The new frame design of the GLOCK 17 Gen5 removed the finger grooves for more versatility but still allows to easily customize its grip by using the different back straps. A flared mag-well and a cutout at the front of the frame give the user more speed during reloading when fractions of a second matter. A reversible enlarged magazine catch, changeable at user level as well as the ambidextrous slide stop lever accommodate left and right-handed operators. The rifling and the crown of the barrel were slightly modified for increased precision.",
                            "https://i.ytimg.com/vi/cL8QaD_W9m4/maxresdefault.jpg",
                            BigDecimal.valueOf(100),
                            LocalDateTime.now()
                    ),
                    new Product(
                            "G19 Gen5 FS",
                            "The GLOCK 19 Gen5 pistol in 9mm Luger is ideal for a more versatile role due to its reduced dimensions. The new frame design without finger grooves still allows to instantly customize its grip to accommodate any hand size by mounting the different back straps. The reversible magazine catch and ambidextrous slide stop lever make it ideal for left and right-handed shooters. The rifling and the crown of the barrel were slightly modified for increased precision..",
                            "https://i.pinimg.com/originals/d8/3d/c7/d83dc74b151313c13fe94d1954c9f2d9.jpg",
                            BigDecimal.valueOf(100),
                            LocalDateTime.now()
                    ),
                    new Product(
                            "H&K VP9 Tactical OR",
                            "The new HK VP9 Tactical OR (Optics Ready) Pistol is the latest addition to the VP family of HK pistols that is both optics-ready and suppressor-ready. This pistol stands out with its 4.7 inch hammer-forged barrel with threaded muzzle and suppressor-height tritium sights. Providing a day or night lower 1/3 co-witness through the window of a red dot sight, the sights remain visible over the top of most suppressors. Also included is an optics cut that, when combined with one of five availble adapter plates, accepts most popular red dot sights. Suppressor and/or optics can be added with no modifications needed.",
                            "https://www.tactical-life.com/wp-content/uploads/sites/8/2018/08/HK-VP9-Beauty-5.jpg",
                            BigDecimal.valueOf(100),
                            LocalDateTime.now()
                    ),
                    new Product(
                            "Sig Sauer P220 LEGION",
                            "The Sig Sauer® P220® Legion SAO Full-Size Semi-Auto Pistol features a stainless steel slide with high-visibility Sig Sauer Electro-Optics X-RAY day/night sights. Custom G-10 grips allow sure handling, while front cocking serrations pair with aggressive checkering on the front strap and under the trigger guard to ensure a solid grip when it matters. An X-Five undercut on the trigger guard lets you get a higher grip for better weapon control. Low-profile levers reduce the risk of snagging when you need to get your weapon out in a hurry. Steel guide rod adds weight to enhance stability. ",
                            "https://i2.wp.com/shootarillusions.com/wp-content/uploads/2021/04/pix679079443.jpg?fit=1000%2C750&ssl=1",
                            BigDecimal.valueOf(100),
                            LocalDateTime.now()
                    )
            ));
        }
        return null;
    }
}

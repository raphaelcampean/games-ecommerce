package com.gamesecommerce.store.config;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.gamesecommerce.store.model.*;
import com.gamesecommerce.store.repository.*;

@Component
public class GameStoreSeeder implements CommandLineRunner {

    private final DeveloperRepository developerRepository;
    private final GenreRepository genreRepository;
    private final PlatformRepository platformRepository;
    private final ProductRepository productRepository;


    public GameStoreSeeder(
            DeveloperRepository developerRepository,
            GenreRepository genreRepository,
            PlatformRepository platformRepository,
            ProductRepository productRepository
    ) {
        this.developerRepository = developerRepository;
        this.genreRepository = genreRepository;
        this.platformRepository = platformRepository;
        this.productRepository = productRepository;
    }


    @Override
    public void run(String... args) {

        if (productRepository.count() > 0) {
            return;
        }


        // Developers

        Developer rockstar = new Developer();
        rockstar.setName("Rockstar Games");
        rockstar.setSlug("rockstar-games");
        rockstar.setDescription("Estúdio conhecido por jogos de mundo aberto.");

        Developer naughty = new Developer();
        naughty.setName("Naughty Dog");
        naughty.setSlug("naughty-dog");
        naughty.setDescription("Estúdio conhecido por jogos de aventura e narrativa.");
        Developer cd = new Developer();
        cd.setName("CD Projekt Red");
        cd.setSlug("cd-projekt-red");
        cd.setDescription("Estúdio conhecido por jogos de RPG.");

        Developer from = new Developer();
        from.setName("FromSoftware");
        from.setSlug("fromsoftware");
        from.setDescription("Estúdio conhecido por jogos de ação e RPG.");

        Developer valve = new Developer();
        valve.setName("Valve");
        valve.setSlug("valve");
        valve.setDescription("Estúdio conhecido por jogos de tiro e aventura.");


        developerRepository.saveAll(
                List.of(rockstar, naughty, cd, from, valve)
        );



        // Genres

        Genre action = createGenre("Action", "action");
        Genre rpg = createGenre("RPG", "rpg");
        Genre adventure = createGenre("Adventure", "adventure");
        Genre horror = createGenre("Horror", "horror");
        Genre fps = createGenre("FPS", "fps");


        genreRepository.saveAll(
                List.of(action, rpg, adventure, horror, fps)
        );



        // Platforms

        Platform pc = createPlatform("PC", "pc");
        Platform ps5 = createPlatform("PlayStation 5", "playstation-5");
        Platform xbox = createPlatform("Xbox Series X", "xbox-series-x");
        Platform switchP = createPlatform("Nintendo Switch", "nintendo-switch");
        Platform ps4 = createPlatform("PlayStation 4", "playstation-4");


        platformRepository.saveAll(
                List.of(pc, ps5, xbox, switchP, ps4)
        );



        // Products

        Product gta = new Product();
        gta.setName("GTA V");
        gta.setSlug("gta-v");
        gta.setPrice(new BigDecimal("99.90"));
        gta.setStockQuantity(50);
        gta.setDeveloper(rockstar);
        gta.setDescription("Grand Theft Auto V é um jogo de ação e aventura em mundo aberto desenvolvido pela Rockstar North e publicado pela Rockstar Games. Lançado em 2013, o jogo se passa na cidade fictícia de Los Santos, inspirada em Los Angeles, e segue a história de três protagonistas enquanto eles se envolvem em atividades criminosas.");
        gta.setImageUrl("https://res.cloudinary.com/dbxubgc5r/image/upload/v1782920350/gta-v_ftt3hg.png");
        gta.setGenres(
                Set.of(action, adventure)
        );
        gta.setPlatforms(
                Set.of(pc, ps5, xbox)
        );
        gta.setActive(true);

        Product tlou = new Product();
        tlou.setName("The Last of Us");
        tlou.setSlug("the-last-of-us");
        tlou.setPrice(new BigDecimal("199.90"));
        tlou.setStockQuantity(20);
        tlou.setImageUrl("https://res.cloudinary.com/dbxubgc5r/image/upload/v1782920321/the-last-of-us_fwdvto.jpg");
        tlou.setDeveloper(naughty);
        tlou.setDescription("The Last of Us é um jogo de ação e aventura em terceira pessoa desenvolvido pela Naughty Dog e publicado pela Sony Computer Entertainment. Lançado em 2013, o jogo se passa em um mundo pós-apocalíptico devastado por uma infecção fúngica que transforma humanos em criaturas agressivas. A história segue Joel, um sobrevivente endurecido, e Ellie, uma jovem imune à infecção, enquanto eles viajam pelos Estados Unidos em busca de segurança e esperança.");
        tlou.setGenres(
                Set.of(adventure, horror)
        );
        tlou.setPlatforms(
                Set.of(ps4, ps5)
        );
        tlou.setActive(true);

        Product cyberpunk = new Product();
        cyberpunk.setName("Cyberpunk 2077");
        cyberpunk.setSlug("cyberpunk-2077");
        cyberpunk.setPrice(new BigDecimal("149.90"));
        cyberpunk.setStockQuantity(30);
        cyberpunk.setDeveloper(cd);
        cyberpunk.setDescription("Cyberpunk 2077 é um jogo de RPG em mundo aberto desenvolvido pela CD Projekt RED e publicado pela CD Projekt. Lançado em 2020, o jogo se passa em Night City, uma cidade futurista e decadente, onde o jogador assume o papel de V, um mercenário que busca um meio de sobreviver em um mundo dominado por corporações e tecnologia avançada.");
        cyberpunk.setImageUrl("https://res.cloudinary.com/dbxubgc5r/image/upload/v1782920353/cyberpunk-2077_owc3fx.jpg");
        cyberpunk.setGenres(
                Set.of(rpg, action)
        );
        cyberpunk.setPlatforms(
                Set.of(pc, ps5, xbox)
        );
        cyberpunk.setActive(true);

        Product elden = new Product();
        elden.setName("Elden Ring");
        elden.setSlug("elden-ring");
        elden.setPrice(new BigDecimal("249.90"));
        elden.setStockQuantity(15);
        elden.setDeveloper(from);
        elden.setDescription("Elden Ring é um jogo de RPG em mundo aberto desenvolvido pela FromSoftware e publicado pela Bandai Namco Entertainment. Lançado em 2020, o jogo se passa em The Lands Between, um mundo fantasy com elementos de dark fantasy e ação.");
        elden.setImageUrl("https://res.cloudinary.com/dbxubgc5r/image/upload/v1782920357/elden-ring_dnzeat.jpg");
        elden.setGenres(
                Set.of(rpg, adventure)
        );
        elden.setPlatforms(
                Set.of(pc, ps5, xbox)
        );
        elden.setActive(true);

        Product halfLife = new Product();
        halfLife.setName("Half Life Alyx");
        halfLife.setSlug("half-life-alyx");
        halfLife.setPrice(new BigDecimal("179.90"));
        halfLife.setStockQuantity(10);
        halfLife.setDeveloper(valve);
        halfLife.setDescription("Half-Life: Alyx é um jogo de tiro em primeira pessoa desenvolvido e publicado pela Valve. Lançado em 2020, o jogo se passa entre os eventos de Half-Life e Half-Life 2, e segue a história de Alyx Vance enquanto ela luta contra a ocupação alienígena da Terra.");
        halfLife.setImageUrl("https://res.cloudinary.com/dbxubgc5r/image/upload/v1782920330/half-life-alyx_ehe9uo.jpg");
        halfLife.setGenres(
                Set.of(fps, adventure)
        );
        halfLife.setPlatforms(
                Set.of(pc)
        );
        halfLife.setActive(true);

        productRepository.saveAll(
                List.of(
                        gta,
                        tlou,
                        cyberpunk,
                        elden,
                        halfLife
                )
        );


        System.out.println("✔ Game store data seeded!");
    }



    private Genre createGenre(String name, String slug) {
        Genre genre = new Genre();
        genre.setName(name);
        genre.setSlug(slug);
        return genre;
    }


    private Platform createPlatform(String name, String slug) {
        Platform platform = new Platform();
        platform.setName(name);
        platform.setSlug(slug);
        return platform;
    }
}
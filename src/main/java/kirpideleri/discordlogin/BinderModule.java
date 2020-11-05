package kirpideleri.discordlogin;

import com.google.inject.*;
import kirpideleri.discordlogin.repositories.user.FileBasedUserRepository;
import kirpideleri.discordlogin.repositories.user.IUserRepository;
import kirpideleri.discordlogin.utils.*;
import net.dv8tion.jda.api.JDA;

public class BinderModule extends AbstractModule {
    private final DiscordLoginPlugin plugin;

    public BinderModule(DiscordLoginPlugin plugin) {
        this.plugin = plugin;
    }

    public Injector createInjector() {
        return Guice.createInjector(this);
    }

    @Override
    protected void configure() {
        this.bind(DiscordLoginPlugin.class).toInstance(this.plugin);
        this.bind(IConfig.class).to(Config.class).in(Scopes.SINGLETON);
        this.bind(IAccountManager.class).to(AccountManager.class).in(Scopes.SINGLETON);
        this.bind(IUserRepository.class).to(FileBasedUserRepository.class).in(Scopes.SINGLETON);
        this.bind(IMessages.class).to(Messages.class).in(Scopes.SINGLETON);
    }
}

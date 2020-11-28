package github.fakandere.discordlogin;

import com.google.inject.*;
import github.fakandere.discordlogin.utils.*;
import github.fakandere.discordlogin.repositories.user.FileBasedUserRepository;
import github.fakandere.discordlogin.repositories.user.IUserRepository;

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

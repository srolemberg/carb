package br.com.samirrolemberg.carb.helper;

import br.com.samirrolemberg.carb.BuildConfig;
import br.com.samirrolemberg.carb.R;
import br.com.samirrolemberg.carb.utils.CustomContext;
import io.realm.Realm;
import io.realm.RealmConfiguration;


/**
 * Created by srolemberg on 9/05/16.
 */
public class RealmHelper {

    private RealmHelper() {
    }

    public static Realm getInstance() {
        final RealmConfiguration realmConfiguration;

        RealmConfiguration.Builder builder = new RealmConfiguration.Builder(CustomContext.getContext());
            builder.name(CustomContext.getContext().getResources().getString(R.string.nome_db));
            if (BuildConfig.DEBUG) {
                builder.deleteRealmIfMigrationNeeded();
            }
        realmConfiguration = builder.build();

        return Realm.getInstance(realmConfiguration);
    }

}

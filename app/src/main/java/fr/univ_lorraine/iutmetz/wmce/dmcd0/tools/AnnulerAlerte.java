/**
 * Affichage de la boîte de dialogue d'alerte RESISTANTE au pivot
 *
 */
package fr.univ_lorraine.iutmetz.wmce.dmcd0.tools;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import androidx.fragment.app.DialogFragment;
import androidx.appcompat.app.AlertDialog;

import fr.univ_lorraine.iutmetz.wmce.dmcd0.R;

public class AnnulerAlerte extends DialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        Activity activite = getActivity();

        AlertDialog.Builder builder;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder = new AlertDialog.Builder(activite, android.R.style.Theme_Material_Dialog_Alert);
        } else {
            builder = new AlertDialog.Builder(activite);
        }

        builder.setMessage(R.string.confirm_phrase)
                .setTitle(R.string.confirm_titre);

        DialogInterface.OnClickListener ecouteur = (DialogInterface.OnClickListener) activite;
        builder.setPositiveButton(R.string.confirm_oui, ecouteur);
        builder.setNegativeButton(R.string.confirm_non, ecouteur);

        return builder.create();
    }

}



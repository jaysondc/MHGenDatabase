package com.ghstudios.android.ui.detail;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ghstudios.android.data.classes.PalicoWeapon;
import com.ghstudios.android.loader.PalicoWeaponLoader;
import com.ghstudios.android.mhgendatabase.R;
import com.ghstudios.android.ui.general.DrawSharpness;

/**
 * Created by Joseph on 7/10/2016.
 */
public class PalicoWeaponDetailFragment extends Fragment implements
        LoaderManager.LoaderCallbacks<PalicoWeapon> {

    public static String EXTRA_WEAPON_ID="WEAPON_ID";

    public static PalicoWeaponDetailFragment newInstance(long id) {
        Bundle args = new Bundle();
        args.putLong(EXTRA_WEAPON_ID, id);
        PalicoWeaponDetailFragment f = new PalicoWeaponDetailFragment();
        f.setArguments(args);
        return f;
    }

    TextView _titleBar, _attackMelee,_attackRanged,_elementMelee,_elementRanged,_elementText,
            _affinityMelee,_affinityRanged,_defenseText,_defenseValue,_blunt,_balance,_creationCost,
            _rarity,_description;
    LinearLayout _sharpnessLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_palico_weapon_detail, parent, false);

        //Get all needed views
        _titleBar = (TextView) v.findViewById(R.id.detail_title_bar_text);
        _attackMelee = (TextView)v.findViewById(R.id.detail_weapon_melee);
        _attackRanged = (TextView)v.findViewById(R.id.detail_weapon_ranged);
        _elementMelee = (TextView)v.findViewById(R.id.detail_weapon_element_melee);
        _elementRanged = (TextView)v.findViewById(R.id.detail_weapon_element_ranged);
        _elementText = (TextView)v.findViewById(R.id.detail_weapon_element_text);
        _affinityMelee = (TextView)v.findViewById(R.id.detail_weapon_affinity_melee);
        _affinityRanged = (TextView)v.findViewById(R.id.detail_weapon_affinity_ranged);

        _defenseText = (TextView)v.findViewById(R.id.detail_weapon_defense_text);
        _defenseValue = (TextView)v.findViewById(R.id.detail_weapon_defense);

        _blunt = (TextView)v.findViewById(R.id.detail_weapon_blunt);
        _balance = (TextView)v.findViewById(R.id.detail_weapon_balance);
        _creationCost = (TextView)v.findViewById(R.id.detail_weapon_creation);
        _rarity = (TextView)v.findViewById(R.id.detail_weapon_rarity);
        _description = (TextView)v.findViewById(R.id.detail_weapon_description);

        _sharpnessLayout = (LinearLayout)v.findViewById(R.id.detail_weapon_sharpness);

        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getLoaderManager().initLoader(R.id.palico_weapon_detail_fragment, getArguments(), this);
    }

    @Override
    public Loader<PalicoWeapon> onCreateLoader(int id, Bundle args) {
        return new PalicoWeaponLoader(getContext(),args.getLong(EXTRA_WEAPON_ID));
    }

    @Override
    public void onLoadFinished(Loader<PalicoWeapon> loader, PalicoWeapon data) {
        updateUI(data);
    }

    @Override
    public void onLoaderReset(Loader<PalicoWeapon> loader) {}


    void updateUI(PalicoWeapon wep){

        _titleBar.setText(wep.getItem().getName());
        _attackMelee.setText(Integer.toString(wep.getAttackMelee()));
        _attackRanged.setText(Integer.toString(wep.getAttackRanged()));
        _elementMelee.setText(Integer.toString(wep.getElementMelee()));
        _elementRanged.setText(Integer.toString(wep.getElementRanged()));

        if(wep.getElement().length()==0)
            _elementText.setText("None");
        else
            _elementText.setText(wep.getElement());

        _affinityMelee.setText(Integer.toString(wep.getAffinityMelee())+"%");
        _affinityRanged.setText(Integer.toString(wep.getAffinityRanged())+"%");

        _blunt.setText(wep.isBlunt()?"Blunt":"Cutting");
        _balance.setText(wep.getBalanceString());

        if(wep.getDefense()==0) {
            _defenseText.setVisibility(View.GONE);
            _defenseValue.setVisibility(View.GONE);
        }
        else
            _defenseValue.setText(Integer.toString(wep.getDefense()));


        _creationCost.setText(Integer.toString(wep.getCreation_cost()));
        _rarity.setText(Integer.toString(wep.getItem().getRarity()));
        _description.setText(wep.getItem().getDescription());


        int color = Color.BLACK;
        switch(wep.getSharpness()){
            case 1:
                color = DrawSharpness.orangeColor;
                break;
            case 2:
                color= Color.YELLOW;
                break;
            case 3:
                color=Color.GREEN;
                break;
            case 4:
                color = DrawSharpness.blueColor;
                break;
            case 5:
                color = Color.WHITE;
                break;
            default:
                break;
        }

        _sharpnessLayout.setBackgroundColor(color);

    }


}

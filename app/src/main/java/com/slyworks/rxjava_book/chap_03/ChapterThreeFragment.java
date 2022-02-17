package com.slyworks.rxjava_book.chap_03;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jakewharton.rxbinding4.InitialValueObservable;
import com.jakewharton.rxbinding4.widget.RxTextView;
import com.slyworks.rxjava_book.R;

import de.hdodenhof.circleimageview.CircleImageView;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;

public class ChapterThreeFragment extends Fragment {
    //region Vars
    private CircleImageView ivResult;
    private TextView tvError;
    private Spinner spinnerCardType;
    private TextView tvCardType;
    private EditText etCardNumber;
    private EditText etExpiryDate_a;
    private EditText etExpiryDate_b;
    private EditText etCVC;
    private Button btnCheck;
    private ConstraintLayout rootView;
    //endregion
    public ChapterThreeFragment() { }

    public static ChapterThreeFragment getInstance() {
       return new ChapterThreeFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_chapter_three, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initViews(view);
        initRx();
    }

    private void initViews(View view){
        rootView = view.findViewById(R.id.rootView);

        tvError = view.findViewById(R.id.tvError_chap_03);
        ivResult = view.findViewById(R.id.ivResult_chap_03);
        tvCardType = view.findViewById(R.id.tvCardType_chap_03);
        etCardNumber = view.findViewById(R.id.etCardNumber_chap_03);
        etExpiryDate_a = view.findViewById(R.id.etExpiryDate_a_chap_03);
        etExpiryDate_b = view.findViewById(R.id.etExpiryDate_b_chap_03);
        etCVC = view.findViewById(R.id.etCVC_chap_03);
        btnCheck = view.findViewById(R.id.btnCheck_chap_03);

        btnCheck.setOnClickListener(v ->{
            toggleImageStatus(true);
            rootView.requestFocus();
        });
    }

    private void initRx(){
        /*Stage 1*/
        InitialValueObservable<CharSequence> etExpiryDateObservable_a =
                RxTextView.textChanges(etExpiryDate_a);

        InitialValueObservable<CharSequence> etExpiryDateObservable_b =
                RxTextView.textChanges(etExpiryDate_b);

        etExpiryDateObservable_a.map(String::valueOf)
                .subscribe(text -> {
                    if(text.length() == 2)
                        etExpiryDate_b.requestFocus();
                });

        Observable<CharSequence> etExpiryDateObservable =
                Observable.combineLatest(etExpiryDateObservable_a, etExpiryDateObservable_b,
                        (valueA,valueB)->{
                            return valueA + "" + valueB;
                        } );

        InitialValueObservable<CharSequence> etCardNumberObservable =
                RxTextView.textChanges(etCardNumber);

        InitialValueObservable<CharSequence> etCVCObservable =
                RxTextView.textChanges(etCVC);

        /*Stage 1a*/
        Observable<String> expirationDateObservable =
                etExpiryDateObservable.map(String::valueOf);

        Observable<String> creditCardNumberObservable =
                etCardNumberObservable.map(String::valueOf);

        Observable<String> CVCObservable =
                etCVCObservable.map(String::valueOf);

        /*Stage 2*/
        /*region ExpirationDate composite validation*/
        Observable<Boolean> isExpirationDateValid =
        expirationDateObservable.map(ValidationUtils::checkExpirationDate);

        Observable<CardType> cardTypeObservable =
        creditCardNumberObservable.map(CardType::getCardTypeFromString);

        /*for setting the textView displaying the type of card from the CardNumber*/
        cardTypeObservable.map(ct ->{
           String _cardType = "not set";
           switch(ct){
               case AMERICA_EXPRESS : _cardType = "America Express";
               case VISA: _cardType = "Visa";
               case MASTER_CARD:  _cardType = "MasterCard";
               case UNKNOWN: _cardType = "unknown";
           }

           return _cardType;
        })
         .subscribe(type -> tvCardType.setText(type));

        /*endregion*/

        /*region CreditCardNumber composite validation*/
        Observable<Boolean> isCardTypeValid =
        cardTypeObservable.map(cardType -> {return cardType != CardType.UNKNOWN; });

        Observable<Boolean> isCheckSumValid =
        creditCardNumberObservable.map(ValidationUtils::convertFromStringToIntArray)
                                  .map(ValidationUtils::checkCardCheckSum);

        Observable<Boolean> isCreditCardNumberValid =
                ValidationUtils.and(isCardTypeValid, isCheckSumValid);
        /*endregion*/

        /*region CVCCode composite validation*/
        Observable<Integer> requiredCVCLength =
                cardTypeObservable.map(CardType::getCVCLength);

       Observable<Integer> CVCInputLength =
                CVCObservable.map(String::valueOf)
                             .map(String::length);

       Observable<Boolean> isCVCCodeValid =
               ValidationUtils.<Integer>equals(requiredCVCLength, CVCInputLength);
       /*endregion*/

        /*Stage 3: tvError Observables*/
        Observable<String> cardTypeErrorObservable =
                isCardTypeValid.map(status ->{
                   if(status) return "";
                   else return "unknown card type";
                });

        Observable<String> checkSumErrorObservable =
                isCheckSumValid.map(status ->{
                    if(status) return "";
                    else return "invalid checksum";
                });

        Observable<String> CVCErrorObservable =
                isCVCCodeValid.map(status ->{
                   if(status) return "";
                   else return "invalid CVC code";
                });

        Observable<String> expirationDataObservable =
                isExpirationDateValid.map(status -> {
                   if(status) return "";
                   else return "invalid expiration date";
                });

        getErrorText(
                cardTypeErrorObservable,
                checkSumErrorObservable,
                CVCErrorObservable,
                expirationDataObservable)
                .subscribe(text -> tvError.setText(text));

        /*endregion*/

       /*Stage 3*/
       Observable<Boolean> isFormValidObservable =
               ValidationUtils.and(
                       isCreditCardNumberValid,
                       isCheckSumValid,
                       isCVCCodeValid
               );

       /*Stage 4:emits true or false */
        isFormValidObservable.
                observeOn(AndroidSchedulers.mainThread())
                .subscribe(areOtherFieldsValid ->{
                    toggleImageStatus(false);
                    btnCheck.setEnabled(areOtherFieldsValid); });
    }

    @SafeVarargs
    private final Observable<String> getErrorText(Observable<String>... observables){
        return ValidationUtils.andStrings(observables);
    }
    private void toggleImageStatus(Boolean status){
        if(status){
            ivResult.setVisibility(View.VISIBLE);

            Glide.with(requireContext())
                 .asDrawable()
                 .load(R.drawable.ic_status_okay_green)
                 .dontTransform()
                 .into(ivResult);
        }else{
            ivResult.setVisibility(View.GONE);

            Glide.with(requireContext())
                 .asGif()
                 .load(R.drawable.warning)
                 .dontTransform()
                 .into(ivResult);
        }
    }

    //region not-understood code
    /*
    * i think it has to do with disabling EditText subscribers while the EditText has focus???
    *
    * private static void showErrorForEditText(EditText editText,
                                             Observable<Boolean> isValid) {
        getShowErrorForEditText(editText, isValid)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(value -> editText.setTextColor(
                        value ? Color.RED : Color.BLACK));
    }

    private static Observable<Boolean> getShowErrorForEditText(EditText editText,
                                                               Observable<Boolean> isValid) {
        // We need refCount because we have two subscribers: otherwise the first will be
        // unsubscribed automatically when second one arrives
        final Observable<Boolean> hasFocus = RxView.focusChanges(editText).publish().refCount();

        final Observable<Boolean> hasHadFocus =
                Observable.concat(
                        Observable.just(false),
                        hasFocus.filter(value -> value).firstElement().toObservable());

        return Observable.combineLatest(
                hasHadFocus, hasFocus, isValid,
                (hasHadFocusValue, hasFocusValue, isValidValue) ->
                        hasHadFocusValue && (!hasFocusValue && !isValidValue));
    }
    * */
    //endregion
}
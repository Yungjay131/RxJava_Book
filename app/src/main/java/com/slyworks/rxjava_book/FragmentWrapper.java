package com.slyworks.rxjava_book;

import androidx.fragment.app.Fragment;

import com.slyworks.rxjava_book.chap_01.ChapterOneFragment;
import com.slyworks.rxjava_book.chap_02.ChapterTwoFragment;
import com.slyworks.rxjava_book.chap_03.ChapterThreeFragment;
import com.slyworks.rxjava_book.chap_04.ChapterFourFragment;

/**
 * Created by Joshua Sylvanus, 8:40 AM, 27-Dec-21.
 */
enum FragmentWrapper {
    WELCOME{
        @Override
        public Fragment newInstance() {
            return WelcomeFragment.newInstance();
        }

        @Override
        public <T> Class<? extends Fragment> getFragmentClass() {
            return WelcomeFragment.class;
        }
    },
    CHAP_01{
        @Override
        public Fragment newInstance() {
            return ChapterOneFragment.NewInstance();
        }

        @Override
        public <T> Class<? extends Fragment> getFragmentClass() {
            return ChapterOneFragment.class;
        }
    },
    CHAP_02{
        @Override
        public Fragment newInstance() {
            return ChapterTwoFragment.newInstance();
        }

        @Override
        public <T> Class<? extends Fragment> getFragmentClass() {
            return ChapterTwoFragment.class;
        }
    },
    CHAP_03{
        @Override
        public Fragment newInstance() {
            return ChapterThreeFragment.getInstance();
        }

        @Override
        public <T> Class<? extends Fragment> getFragmentClass() {
            return ChapterThreeFragment.class;
        }
    },
    CHAP_04{
        @Override
        public Fragment newInstance() {
            return ChapterFourFragment.getInstance();
        }

        @Override
        public <T> Class<? extends Fragment> getFragmentClass() {
            return ChapterFourFragment.class;
        }
    };

    public abstract Fragment newInstance();
    public abstract <T> Class<? extends Fragment> getFragmentClass();

}

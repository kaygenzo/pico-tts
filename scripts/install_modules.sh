#!/bin/sh

git submodule add -b master https://android.googlesource.com/platform/external/svox
git submodule add -b master https://android.googlesource.com/platform/frameworks/base
git submodule add -b master https://android.googlesource.com/platform/libnativehelper
git submodule add -b master https://android.googlesource.com/platform/system/core
git submodule add -b master https://android.googlesource.com/platform/system/logging
git submodule add -b master https://android.googlesource.com/platform/external/expat
git submodule add -b master https://android.googlesource.com/platform/build platform_build
git submodule add -b lineage-20.0 https://github.com/LineageOS/android_hardware_lineage_compat.git

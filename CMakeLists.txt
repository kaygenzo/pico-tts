cmake_minimum_required(VERSION 3.22.1)

project("picotts")
set(CMAKE_C_STANDARD 11)
set(CMAKE_CXX_STANDARD 17)

add_compile_options(
#        // As of 2.4.6, xmlparse.c includes expat_config.h *before* including
#        // any system header file, meaning that __BIONIC__ and __GLIBC__ and so
#        // on aren't set, but our modifications to have one expat_config.h for
#        // all targets rely on those defines.
        -include stdio.h
)

include_directories(
        svox/pico/compat/include
        base/core/jni/include
        libnativehelper/include
        core/libutils/include
        core/libcutils/include
        core/libsystem/include
        logging/liblog/include
        expat
        svox/pico/lib
        expat/expat/lib
        overlays
)

find_library(log-lib log)

# TTSCOMPAT

add_library(
        ttscompat
        SHARED
        svox/pico/compat/jni/com_android_tts_compat_SynthProxy.cpp
)
target_link_libraries(ttscompat ${log-lib})

# SVOXPICO

add_library(
        svoxpico
        STATIC
        svox/pico/lib/picoacph.c
        svox/pico/lib/picoapi.c
        svox/pico/lib/picobase.c
        svox/pico/lib/picocep.c
        svox/pico/lib/picoctrl.c
        svox/pico/lib/picodata.c
        svox/pico/lib/picodbg.c
        svox/pico/lib/picoextapi.c
        svox/pico/lib/picofftsg.c
        svox/pico/lib/picokdbg.c
        svox/pico/lib/picokdt.c
        svox/pico/lib/picokfst.c
        svox/pico/lib/picoklex.c
        svox/pico/lib/picoknow.c
        svox/pico/lib/picokpdf.c
        svox/pico/lib/picokpr.c
        svox/pico/lib/picoktab.c
        svox/pico/lib/picoos.c
        svox/pico/lib/picopal.c
        svox/pico/lib/picopam.c
        svox/pico/lib/picopr.c
        svox/pico/lib/picorsrc.c
        svox/pico/lib/picosa.c
        svox/pico/lib/picosig.c
        svox/pico/lib/picosig2.c
        svox/pico/lib/picospho.c
        svox/pico/lib/picotok.c
        svox/pico/lib/picotrns.c
        svox/pico/lib/picowa.c
)

# EXPAT

add_library(
        expat
        STATIC
        expat/expat/lib/xmlparse.c
        expat/expat/lib/xmlrole.c
        expat/expat/lib/xmltok.c
)

# LIB TTSPICO

add_library(
        ttspico
        SHARED
        svox/pico/tts/com_svox_picottsengine.cpp
        svox/pico/tts/svox_ssml_parser.cpp
        android_hardware_lineage_compat/libcutils/strdup8to16.cpp
        android_hardware_lineage_compat/libcutils/strdup16to8.cpp
)

target_link_libraries(ttspico svoxpico expat ${log-lib})
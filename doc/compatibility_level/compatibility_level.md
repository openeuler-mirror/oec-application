# openEuler 软件包兼容性分级

openEuler 对软件包的兼容性进行分级管理，其中：

- level1: 软件包及软件包 API、ABI 在某个 LTS 版本的生命周期保持不变，跨 LTS 版本不保证。
- level2: 软件包及软件包 API、ABI 在某个 SP 版本的生命周期保持不变，跨 SP 版本不保证。
- level3: 版本升级兼容性不做保证。

| source package | level | bin package | status |
|---|---|---|---|
| texlive-base-20180414-28.oe1.src | leve3 | texlive-context-20180414-28.oe1.noarch  |  |
| texlive-base-20180414-28.oe1.src | leve3 | texlive-lib-20180414-28.oe1.aarch64  |  |
| texlive-base-20180414-28.oe1.src | leve3 | texlive-metapost-20180414-28.oe1.aarch64 |  |
| texlive-2018-22.oe1.src | leve3 | texlive-chemmacros-svn45164-22.oe1.noarch  |  |
| texlive-2018-22.oe1.src | leve3 | texlive-cmsrb-svn46588-22.oe1.noarch  |  |
| texlive-2018-22.oe1.src | leve3 | texlive-dsserif-svn47570-22.oe1.noarch  |  |
| texlive-2018-22.oe1.src | leve3 | texlive-leadsheets-svn45405-22.oe1.noarch  |  |
| texlive-2018-22.oe1.src | leve3 | texlive-pgf-svn44231-22.oe1.noarch  |  |
| texlive-2018-22.oe1.src | leve3 | texlive-pgfplots-svn47373-22.oe1.noarch  |  |
| texlive-2018-22.oe1.src | leve3 | texlive-stickstoo-svn47858-22.oe1.noarch  |  |
| texlive-2018-22.oe1.src | leve3 | texlive-tikzpeople-svn43978-22.oe1.noarch  |  |
| texlive-2018-22.oe1.src | leve3 | texlive-xsim-svn46634-22.oe1.noarch  |  |
| texlive-2018-22.oe1.src | leve3 | texlive-yfonts-t1-svn36013-22.oe1.noarch |  |
| glibc-2.28-36.oe1.src | leve1 | glibc-2.28-36.oe1.aarch64  |  |
| glibc-2.28-36.oe1.src | leve1 | libnsl-2.28-36.oe1.aarch64  |  |
| glibc-2.28-36.oe1.src | leve1 | nss_modules-2.28-36.oe1.aarch64 |  |
| coreutils-8.31-4.oe1.src | leve2 | coreutils-8.31-4.oe1.aarch64 |  |
| bash-5.0-12.oe1.src | leve2 |  |  |
| gcc-7.3.0-20190804.h31.oe1.src | leve1 | gcc-7.3.0-20190804.h31.oe1.aarch64  |  |
| gcc-7.3.0-20190804.h31.oe1.src | leve1 | gcc-plugin-devel-7.3.0-20190804.h31.oe1.aarch64  |  |
| gcc-7.3.0-20190804.h31.oe1.src | leve1 | libasan-7.3.0-20190804.h31.oe1.aarch64  |  |
| gcc-7.3.0-20190804.h31.oe1.src | leve1 | libatomic-7.3.0-20190804.h31.oe1.aarch64  |  |
| gcc-7.3.0-20190804.h31.oe1.src | leve1 | libgcc-7.3.0-20190804.h31.oe1.aarch64  |  |
| gcc-7.3.0-20190804.h31.oe1.src | leve1 | libgfortran-7.3.0-20190804.h31.oe1.aarch64  |  |
| gcc-7.3.0-20190804.h31.oe1.src | leve1 | libgomp-7.3.0-20190804.h31.oe1.aarch64  |  |
| gcc-7.3.0-20190804.h31.oe1.src | leve1 | libitm-7.3.0-20190804.h31.oe1.aarch64  |  |
| gcc-7.3.0-20190804.h31.oe1.src | leve1 | libobjc-7.3.0-20190804.h31.oe1.aarch64  |  |
| gcc-7.3.0-20190804.h31.oe1.src | leve1 | libstdc++-7.3.0-20190804.h31.oe1.aarch64  |  |
| gcc-7.3.0-20190804.h31.oe1.src | leve1 | libtsan-7.3.0-20190804.h31.oe1.aarch64 |  |
| perl-5.28.0-434.oe1.src | leve3 | perl-libs-5.28.0-434.oe1.aarch64 |  |
| man-db-2.8.7-5.oe1.src | leve3 | man-db-2.8.7-5.oe1.aarch64 |  |
| texinfo-6.6-2.oe1.src | leve3 |  |  |
| pkgconf-1.6.3-6.oe1.src | leve3 | pkgconf-1.6.3-6.oe1.aarch64 |  |
| glib2-2.62.1-1.oe1.src | leve2 | glib2-2.62.1-1.oe1.aarch64  |  |
| glib2-2.62.1-1.oe1.src | leve2 | glib2-devel-2.62.1-1.oe1.aarch64 |  |
| python3-3.7.4-8.oe1.src | leve1 | python3-3.7.4-8.oe1.aarch64  |  |
| python3-3.7.4-8.oe1.src | leve1 | python3-debug-3.7.4-8.oe1.aarch64 |  |
| zlib-1.2.11-17.oe1.src | level1 | minizip-1.2.11-17.oe1.aarch64  |  |
| zlib-1.2.11-17.oe1.src | level1 | zlib-1.2.11-17.oe1.aarch64 |  |
| systemd-243-18.oe1.src | level1 | systemd-243-18.oe1.aarch64  |  |
| systemd-243-18.oe1.src | level1 | systemd-container-243-18.oe1.aarch64  |  |
| systemd-243-18.oe1.src | level1 | systemd-libs-243-18.oe1.aarch64 |  |
| python2-2.7.16-15.oe1.src | leve2 | python2-2.7.16-15.oe1.aarch64  |  |
| python2-2.7.16-15.oe1.src | leve2 | python2-debug-2.7.16-15.oe1.aarch64 |  |
| openssl-1.1.1d-9.oe1.src | level1 | openssl-libs-1.1.1d-9.oe1.aarch64 |  |
| libX11-1.6.9-2.oe1.src | level1 | libX11-1.6.9-2.oe1.aarch64 |  |
| libxml2-2.9.8-9.oe1.src | level1 | libxml2-2.9.8-9.oe1.aarch64  |  |
| libxml2-2.9.8-9.oe1.src | level1 | python2-libxml2-2.9.8-9.oe1.aarch64  |  |
| libxml2-2.9.8-9.oe1.src | level1 | python3-libxml2-2.9.8-9.oe1.aarch64 |  |
| perl-Exporter-5.73-420.oe1.src | leve3 |  |  |
| perl-Carp-1.50-418.oe1.src | leve3 |  |  |
| cairo-1.15.14-3.oe1.src |  | cairo-1.15.14-3.oe1.aarch64  | Subject to modification |
| cairo-1.15.14-3.oe1.src |  | cairo-devel-1.15.14-3.oe1.aarch64 | Subject to modification |
| util-linux-2.34-8.oe1.src | leve3 | libblkid-2.34-8.oe1.aarch64  |  |
| util-linux-2.34-8.oe1.src | leve3 | libfdisk-2.34-8.oe1.aarch64  |  |
| util-linux-2.34-8.oe1.src | leve3 | libmount-2.34-8.oe1.aarch64  |  |
| util-linux-2.34-8.oe1.src | leve3 | libsmartcols-2.34-8.oe1.aarch64  |  |
| util-linux-2.34-8.oe1.src | leve3 | libuuid-2.34-8.oe1.aarch64 |  |
| gdk-pixbuf2-2.38.0-9.oe1.src |  | gdk-pixbuf2-2.38.0-9.oe1.aarch64 | Subject to modification |
| qt5-qtbase-5.11.1-9.oe1.src |  | qt5-qtbase-5.11.1-9.oe1.aarch64  | Subject to modification |
| qt5-qtbase-5.11.1-9.oe1.src |  | qt5-qtbase-devel-5.11.1-9.oe1.aarch64  | Subject to modification |
| qt5-qtbase-5.11.1-9.oe1.src |  | qt5-qtbase-gui-5.11.1-9.oe1.aarch64 | Subject to modification |
| pcp-4.1.3-3.oe1.src | leve2 | pcp-4.1.3-3.oe1.aarch64 |  |
| pango-1.43.0-3.oe1.src |  | pango-1.43.0-3.oe1.aarch64 | Subject to modification |
| libselinux-2.9-1.oe1.src | leve2 | libselinux-2.9-1.oe1.aarch64 |  |
| gtk3-3.24.1-3.oe1.src | leve2 | gtk3-3.24.1-3.oe1.aarch64  |  |
| gtk3-3.24.1-3.oe1.src | leve2 | gtk3-devel-3.24.1-3.oe1.aarch64 |  |
| dbus-1.12.16-13.oe1.src | level1 | dbus-libs-1.12.16-13.oe1.aarch64 |  |
| atk-2.30.0-3.oe1.src | leve2 | atk-2.30.0-3.oe1.aarch64 |  |
| libXext-1.3.4-2.oe1.src | leve2 | libXext-1.3.4-2.oe1.aarch64 |  |
| libxcrypt-4.4.8-4.oe1.src | leve2 | libxcrypt-4.4.8-4.oe1.aarch64 |  |
| perl-PathTools-3.75-4.oe1.src | leve3 |  |  |
| ncurses-6.1-14.oe1.src | leve2 | ncurses-6.1-14.oe1.aarch64  |  |
| ncurses-6.1-14.oe1.src | leve2 | ncurses-libs-6.1-14.oe1.aarch64 |  |
| freetype-2.9.1-5.oe1.src | leve3 | freetype-2.9.1-5.oe1.aarch64 |  |
| perl-Getopt-Long-2.50-419.oe1.src | leve3 |  |  |
| python-six-1.12.0-1.oe1.src | leve3 |  |  |
| fontconfig-2.13.1-3.oe1.src | leve3 | fontconfig-2.13.1-3.oe1.aarch64 |  |
| python-setuptools-40.4.3-4.oe1.src | leve3 |  |  |
| libglvnd-1.2.0-3.oe1.src |  | libglvnd-1.2.0-3.oe1.aarch64 | Subject to modification |
| libvirt-5.5.0-6.oe1.src | leve2 | libvirt-wireshark-5.5.0-6.oe1.aarch64 |  |
| e2fsprogs-1.45.3-4.oe1.src | leve2 | e2fsprogs-1.45.3-4.oe1.aarch64 |  |
| krb5-1.17-9.oe1.src | level1 | krb5-1.17-9.oe1.aarch64  |  |
| krb5-1.17-9.oe1.src | level1 | krb5-libs-1.17-9.oe1.aarch64  |  |
| krb5-1.17-9.oe1.src | level1 | krb5-server-1.17-9.oe1.aarch64 |  |
| nss-3.40.1-11.oe1.src | level1 | nss-3.40.1-11.oe1.aarch64  |  |
| nss-3.40.1-11.oe1.src | level1 | nss-softokn-3.40.1-11.oe1.aarch64  |  |
| nss-3.40.1-11.oe1.src | level1 | nss-util-3.40.1-11.oe1.aarch64 |  |
| samba-4.11.6-5.oe1.src | leve2 | libsmbclient-4.11.6-5.oe1.aarch64  |  |
| samba-4.11.6-5.oe1.src | leve2 | libwbclient-4.11.6-5.oe1.aarch64  |  |
| samba-4.11.6-5.oe1.src | leve2 | python3-samba-4.11.6-5.oe1.aarch64  |  |
| samba-4.11.6-5.oe1.src | leve2 | samba-4.11.6-5.oe1.aarch64  |  |
| samba-4.11.6-5.oe1.src | leve2 | samba-client-4.11.6-5.oe1.aarch64  |  |
| samba-4.11.6-5.oe1.src | leve2 | samba-common-4.11.6-5.oe1.aarch64  |  |
| samba-4.11.6-5.oe1.src | leve2 | samba-dc-4.11.6-5.oe1.aarch64  |  |
| samba-4.11.6-5.oe1.src | leve2 | samba-dc-provision-4.11.6-5.oe1.aarch64  |  |
| samba-4.11.6-5.oe1.src | leve2 | samba-libs-4.11.6-5.oe1.aarch64  |  |
| samba-4.11.6-5.oe1.src | leve2 | samba-test-4.11.6-5.oe1.aarch64  |  |
| samba-4.11.6-5.oe1.src | leve2 | samba-winbind-4.11.6-5.oe1.aarch64  |  |
| samba-4.11.6-5.oe1.src | leve2 | samba-winbind-modules-4.11.6-5.oe1.aarch64 |  |
| xz-5.2.4-10.oe1.src | level1 | xz-libs-5.2.4-10.oe1.aarch64 |  |
| ceph-12.2.8-6.oe1.src | leve2 | ceph-base-12.2.8-6.oe1.aarch64  | Subject to modification |
| ceph-12.2.8-6.oe1.src | leve2 | libcephfs2-12.2.8-6.oe1.aarch64  |  |
| ceph-12.2.8-6.oe1.src | leve2 | librados2-12.2.8-6.oe1.aarch64  |  |
| ceph-12.2.8-6.oe1.src | leve2 | libradosstriper1-12.2.8-6.oe1.aarch64  |  |
| ceph-12.2.8-6.oe1.src | leve2 | librbd1-12.2.8-6.oe1.aarch64  |  |
| ceph-12.2.8-6.oe1.src | leve2 | librgw2-12.2.8-6.oe1.aarch64 |  |
| gnutls-3.6.9-5.oe1.src | level1 | gnutls-3.6.9-5.oe1.aarch64 |  |
| perl-constant-1.33-421.oe1.src | leve3 |  |  |
| curl-7.66.0-2.oe1.src | level1 | curl-7.66.0-2.oe1.aarch64 |  |
| rpm-4.15.1-12.oe1.src | leve3 | rpm-libs-4.15.1-12.oe1.aarch64 |  |
| shadow-4.7-10.oe1.src | leve2 |  |  |
| libjpeg-turbo-2.0.0-4.oe1.src |  | libjpeg-turbo-2.0.0-4.oe1.aarch64 | Subject to modification |
| boost-1.66.0-18.oe1.src | leve2 | boost-atomic-1.66.0-18.oe1.aarch64  |  |
| boost-1.66.0-18.oe1.src | leve2 | boost-chrono-1.66.0-18.oe1.aarch64  |  |
| boost-1.66.0-18.oe1.src | leve2 | boost-container-1.66.0-18.oe1.aarch64  |  |
| boost-1.66.0-18.oe1.src | leve2 | boost-context-1.66.0-18.oe1.aarch64  |  |
| boost-1.66.0-18.oe1.src | leve2 | boost-coroutine-1.66.0-18.oe1.aarch64  |  |
| boost-1.66.0-18.oe1.src | leve2 | boost-date-time-1.66.0-18.oe1.aarch64  |  |
| boost-1.66.0-18.oe1.src | leve2 | boost-fiber-1.66.0-18.oe1.aarch64  |  |
| boost-1.66.0-18.oe1.src | leve2 | boost-filesystem-1.66.0-18.oe1.aarch64  |  |
| boost-1.66.0-18.oe1.src | leve2 | boost-graph-1.66.0-18.oe1.aarch64  |  |
| boost-1.66.0-18.oe1.src | leve2 | boost-iostreams-1.66.0-18.oe1.aarch64  |  |
| boost-1.66.0-18.oe1.src | leve2 | boost-locale-1.66.0-18.oe1.aarch64  |  |
| boost-1.66.0-18.oe1.src | leve2 | boost-log-1.66.0-18.oe1.aarch64  |  |
| boost-1.66.0-18.oe1.src | leve2 | boost-math-1.66.0-18.oe1.aarch64  |  |
| boost-1.66.0-18.oe1.src | leve2 | boost-numpy2-1.66.0-18.oe1.aarch64  |  |
| boost-1.66.0-18.oe1.src | leve2 | boost-numpy3-1.66.0-18.oe1.aarch64  |  |
| boost-1.66.0-18.oe1.src | leve2 | boost-program-options-1.66.0-18.oe1.aarch64  |  |
| boost-1.66.0-18.oe1.src | leve2 | boost-python2-1.66.0-18.oe1.aarch64  |  |
| boost-1.66.0-18.oe1.src | leve2 | boost-python3-1.66.0-18.oe1.aarch64  |  |
| boost-1.66.0-18.oe1.src | leve2 | boost-random-1.66.0-18.oe1.aarch64  |  |
| boost-1.66.0-18.oe1.src | leve2 | boost-regex-1.66.0-18.oe1.aarch64  |  |
| boost-1.66.0-18.oe1.src | leve2 | boost-serialization-1.66.0-18.oe1.aarch64  |  |
| boost-1.66.0-18.oe1.src | leve2 | boost-signals-1.66.0-18.oe1.aarch64  |  |
| boost-1.66.0-18.oe1.src | leve2 | boost-stacktrace-1.66.0-18.oe1.aarch64  |  |
| boost-1.66.0-18.oe1.src | leve2 | boost-system-1.66.0-18.oe1.aarch64  |  |
| boost-1.66.0-18.oe1.src | leve2 | boost-test-1.66.0-18.oe1.aarch64  |  |
| boost-1.66.0-18.oe1.src | leve2 | boost-thread-1.66.0-18.oe1.aarch64  |  |
| boost-1.66.0-18.oe1.src | leve2 | boost-timer-1.66.0-18.oe1.aarch64  |  |
| boost-1.66.0-18.oe1.src | leve2 | boost-type_erasure-1.66.0-18.oe1.aarch64  |  |
| boost-1.66.0-18.oe1.src | leve2 | boost-wave-1.66.0-18.oe1.aarch64 |  |
| ruby-2.5.1-104.oe1.src | leve3 | ruby-2.5.1-104.oe1.aarch64 |  |
| libpng-1.6.36-4.oe1.src | leve2 | libpng-1.6.36-4.oe1.aarch64 |  |
| expat-2.2.6-5.oe1.src | leve2 | expat-2.2.6-5.oe1.aarch64 |  |
| google-noto-cjk-fonts-20170602-9.oe1.src | leve3 |  |  |
| acl-2.2.53-7.oe1.src | level1 | libacl-2.2.53-7.oe1.aarch64 |  |
| sqlite-3.24.0-9.oe1.src | leve2 | sqlite-3.24.0-9.oe1.aarch64 |  |
| lvm2-2.02.181-8.oe1.src | leve2 | device-mapper-1.02.150-8.oe1.aarch64  |  |
| lvm2-2.02.181-8.oe1.src | leve2 | device-mapper-event-1.02.150-8.oe1.aarch64  |  |
| lvm2-2.02.181-8.oe1.src | leve2 | lvm2-2.02.181-8.oe1.aarch64 |  |
| pam-1.3.1-8.oe1.src | level1 | pam-1.3.1-8.oe1.aarch64 |  |
| readline-7.0-13.oe1.src | leve2 | readline-7.0-13.oe1.aarch64 |  |
| gtk2-2.24.32-7.oe1.src | leve2 | gtk2-2.24.32-7.oe1.aarch64 |  |
| abrt-2.13.0-4.oe1.src | leve3 | abrt-gui-2.13.0-4.oe1.aarch64  |  |
| abrt-2.13.0-4.oe1.src | leve3 | abrt-libs-2.13.0-4.oe1.aarch64 |  |
| bzip2-1.0.8-3.oe1.src | level1 | bzip2-1.0.8-3.oe1.aarch64 |  |
| audit-3.0-5.oe1.src | leve2 | audit-libs-3.0-5.oe1.aarch64 |  |
| cyrus-sasl-2.1.27-10.oe1.src | leve2 | cyrus-sasl-2.1.27-10.oe1.aarch64  |  |
| cyrus-sasl-2.1.27-10.oe1.src | leve2 | cyrus-sasl-lib-2.1.27-10.oe1.aarch64  |  |
| cyrus-sasl-2.1.27-10.oe1.src | leve2 | cyrus-sasl-sql-2.1.27-10.oe1.aarch64 |  |
| libcap-2.27-1.oe1.src | leve2 | libcap-2.27-1.oe1.aarch64 |  |
| avahi-0.7-21.oe1.src |  | avahi-0.7-21.oe1.aarch64  | Subject to modification |
| avahi-0.7-21.oe1.src |  | avahi-compat-howl-0.7-21.oe1.aarch64  | Subject to modification |
| avahi-0.7-21.oe1.src |  | avahi-compat-libdns_sd-0.7-21.oe1.aarch64  | Subject to modification |
| avahi-0.7-21.oe1.src |  | avahi-glib-0.7-21.oe1.aarch64  | Subject to modification |
| avahi-0.7-21.oe1.src |  | avahi-gobject-0.7-21.oe1.aarch64  | Subject to modification |
| avahi-0.7-21.oe1.src |  | avahi-libs-0.7-21.oe1.aarch64  | Subject to modification |
| avahi-0.7-21.oe1.src |  | avahi-ui-0.7-21.oe1.aarch64  | Subject to modification |
| avahi-0.7-21.oe1.src |  | avahi-ui-gtk3-0.7-21.oe1.aarch64 | Subject to modification |
| mesa-18.2.2-6.oe1.src | leve2 | mesa-libEGL-18.2.2-6.oe1.aarch64  |  |
| mesa-18.2.2-6.oe1.src | leve2 | mesa-libGL-18.2.2-6.oe1.aarch64  |  |
| mesa-18.2.2-6.oe1.src | leve2 | mesa-libOSMesa-18.2.2-6.oe1.aarch64  |  |
| mesa-18.2.2-6.oe1.src | leve2 | mesa-libgbm-18.2.2-6.oe1.aarch64  |  |
| mesa-18.2.2-6.oe1.src | leve2 | mesa-libglapi-18.2.2-6.oe1.aarch64  |  |
| mesa-18.2.2-6.oe1.src | leve2 | mesa-libxatracker-18.2.2-6.oe1.aarch64  |  |
| mesa-18.2.2-6.oe1.src | leve2 | mesa-omx-drivers-18.2.2-6.oe1.aarch64  |  |
| mesa-18.2.2-6.oe1.src | leve2 | mesa-vdpau-drivers-18.2.2-6.oe1.aarch64  |  |
| mesa-18.2.2-6.oe1.src | leve2 | mesa-vulkan-drivers-18.2.2-6.oe1.aarch64 |  |
| popt-1.16-17.oe1.src | leve2 | popt-1.16-17.oe1.aarch64 |  |
| openldap-2.4.46-15.oe1.src | leve2 | openldap-2.4.46-15.oe1.aarch64 |  |
| libICE-1.0.10-2.oe1.src | leve2 | libICE-1.0.10-2.oe1.aarch64 |  |
| perl-File-Temp-0.230.800-4.oe1.src | leve3 |  |  |
| grep-3.4-0.oe1.src | leve3 |  |  |
| libSM-1.2.3-2.oe1.src |  | libSM-1.2.3-2.oe1.aarch64 | Subject to modification |
| libXfixes-5.0.3-11.oe1.src | leve3 | libXfixes-5.0.3-11.oe1.aarch64 |  |
| perl-File-Path-2.16-4.oe1.src | leve3 |  |  |
| libgcrypt-1.8.3-5.oe1.src | level1 | libgcrypt-1.8.3-5.oe1.aarch64 |  |
| libgpg-error-1.35-3.oe1.src | leve2 | libgpg-error-1.35-3.oe1.aarch64 |  |
| nspr-4.20.0-4.oe1.src | leve3 | nspr-4.20.0-4.oe1.aarch64 |  |
| polkit-0.116-4.oe1.src | leve3 | polkit-libs-0.116-4.oe1.aarch64 |  |
| fonts-rpm-macros-2.0.2-2.oe1.src | leve3 |  |  |
| libXi-1.7.9-11.oe1.src |  | libXi-1.7.9-11.oe1.aarch64 | Subject to modification |
| perl-Scalar-List-Utils-1.52-2.oe1.src | leve3 |  |  |
| chkconfig-1.10-8.oe1.src | leve3 |  |  |
| libnl3-3.5.0-1.oe1.src | leve2 | libnl3-3.5.0-1.oe1.aarch64 |  |
| sed-4.7-0.oe1.src | leve3 |  |  |
| urw-base35-fonts-20170801-11.oe1.src | leve3 |  |  |
| libcanberra-0.30-25.oe1.src | leve3 | libcanberra-0.30-25.oe1.aarch64  |  |
| libcanberra-0.30-25.oe1.src | leve3 | libcanberra-gtk2-0.30-25.oe1.aarch64  |  |
| libcanberra-0.30-25.oe1.src | leve3 | libcanberra-gtk3-0.30-25.oe1.aarch64 |  |
| qt5-qtdeclarative-5.11.1-5.oe1.src | leve3 | qt5-qtdeclarative-5.11.1-5.oe1.aarch64  |  |
| qt5-qtdeclarative-5.11.1-5.oe1.src | leve3 | qt5-qtdeclarative-devel-5.11.1-5.oe1.aarch64 |  |
| json-glib-1.4.4-2.oe1.src | leve2 | json-glib-1.4.4-2.oe1.aarch64 |  |
| libXrender-0.9.10-10.oe1.src |  | libXrender-0.9.10-10.oe1.aarch64 | Subject to modification |
| gawk-5.0.1-2.oe1.src | leve3 |  |  |
| libtalloc-2.3.0-0.oe1.src | leve2 | libtalloc-2.3.0-0.oe1.aarch64  |  |
| libtalloc-2.3.0-0.oe1.src | leve2 | python3-talloc-2.3.0-0.oe1.aarch64 |  |
| libtool-2.4.6-32.oe1.src | leve2 | libtool-ltdl-2.4.6-32.oe1.aarch64 |  |
| perl-Encode-2.98-9.oe1.src | leve3 |  |  |
| libcap-ng-0.7.9-7.oe1.src | leve2 | libcap-ng-0.7.9-7.oe1.aarch64 |  |
| libsoup-2.66.1-1.oe1.src | leve2 | libsoup-2.66.1-1.oe1.aarch64 |  |
| cmake-3.12.1-5.oe1.src | leve3 |  |  |
| libXt-1.1.5-11.oe1.src |  | libXt-1.1.5-11.oe1.aarch64 | Subject to modification |
| libxcb-1.13.1-2.oe1.src |  | libxcb-1.13.1-2.oe1.aarch64 | Subject to modification |
| pygobject3-3.30.1-2.oe1.src | leve2 |  |  |
| wayland-1.17.0-2.oe1.src |  | wayland-1.17.0-2.oe1.aarch64 | Subject to modification |
| alsa-lib-1.1.6-6.oe1.src |  | alsa-lib-1.1.6-6.oe1.aarch64 | Subject to modification |
| pcre-8.43-5.oe1.src | leve3 | pcre-8.43-5.oe1.aarch64 |  |
| libdrm-2.4.95-2.oe1.src |  | libdrm-2.4.95-2.oe1.aarch64 | Subject to modification |
| libtiff-4.1.0-1.oe1.src | leve2 | libtiff-4.1.0-1.oe1.aarch64 |  |
| cups-2.2.8-8.oe1.src | leve3 | cups-libs-2.2.8-8.oe1.aarch64 |  |
| gmp-6.1.2-10.oe1.src | leve2 | gmp-6.1.2-10.oe1.aarch64  |  |
| gmp-6.1.2-10.oe1.src | leve2 | gmp-c++-6.1.2-10.oe1.aarch64 |  |
| libXrandr-1.5.1-10.oe1.src |  | libXrandr-1.5.1-10.oe1.aarch64 | Subject to modification |
| postgresql-10.5-12.oe1.src | leve3 | postgresql-libs-10.5-12.oe1.aarch64 |  |
| elfutils-0.177-3.oe1.src | level1 | elfutils-0.177-3.oe1.aarch64 |  |
| libtdb-1.4.2-2.oe1.src | leve2 | libtdb-1.4.2-2.oe1.aarch64 |  |
| gstreamer1-1.14.4-4.oe1.src |  | gstreamer1-1.14.4-4.oe1.aarch64 | Subject to modification |
| lcms2-2.9-7.oe1.src | leve2 | lcms2-2.9-7.oe1.aarch64 |  |
| libXcomposite-0.4.4-17.oe1.src | leve3 | libXcomposite-0.4.4-17.oe1.aarch64 |  |
| libXdamage-1.1.4-18.oe1.src | leve3 | libXdamage-1.1.4-18.oe1.aarch64 |  |
| libreport-2.10.1-7.oe1.src | leve3 | libreport-2.10.1-7.oe1.aarch64 |  |
| perl-Data-Dumper-2.172-3.oe1.src | leve3 |  |  |
| xorg-x11-proto-devel-2018.4-3.oe1.src | leve2 |  |  |
| libdb-5.3.28-35.oe1.src | leve3 | libdb-5.3.28-35.oe1.aarch64 |  |
| perl-Pod-Usage-1.69-418.oe1.src | leve3 |  |  |
| icu-62.1-5.oe1.src | leve3 | libicu-62.1-5.oe1.aarch64 |  |
| php-7.2.10-3.oe1.src | leve3 | php-7.2.10-3.oe1.aarch64  |  |
| php-7.2.10-3.oe1.src | leve3 | php-embedded-7.2.10-3.oe1.aarch64 |  |
| libtevent-0.10.1-1.oe1.src | leve2 | libtevent-0.10.1-1.oe1.aarch64 |  |
| libxslt-1.1.32-7.oe1.src | leve2 | libxslt-1.1.32-7.oe1.aarch64  |  |
| libxslt-1.1.32-7.oe1.src | leve2 | python2-libxslt-1.1.32-7.oe1.aarch64 |  |
| lua-5.3.5-4.oe1.src | leve3 | lua-5.3.5-4.oe1.aarch64 |  |
| selinux-policy-3.14.2-52.oe1.src | leve3 |  |  |
| pulseaudio-12.2-3.oe1.src |  | pulseaudio-12.2-3.oe1.aarch64 | Subject to modification |
| yajl-2.1.0-12.oe1.src | leve2 | yajl-2.1.0-12.oe1.aarch64 |  |
| bind-9.11.4-13.oe1.src | leve2 | bind-export-libs-9.11.4-13.oe1.aarch64  |  |
| bind-9.11.4-13.oe1.src | leve2 | bind-libs-9.11.4-13.oe1.aarch64  |  |
| bind-9.11.4-13.oe1.src | leve2 | bind-libs-lite-9.11.4-13.oe1.aarch64  |  |
| bind-9.11.4-13.oe1.src | leve2 | bind-pkcs11-9.11.4-13.oe1.aarch64 |  |
| kmod-25-6.oe1.src | leve2 | kmod-libs-25-6.oe1.aarch64 |  |
| libnsl2-1.2.0-4.oe1.src | leve2 | libnsl2-1.2.0-4.oe1.aarch64 |  |
| jansson-2.11-4.oe1.src | leve2 | jansson-2.11-4.oe1.aarch64 |  |
| libffi-3.3-7.oe1.src |  | libffi-3.3-7.oe1.aarch64 | Subject to modification |
| libusbx-1.0.22-2.oe1.src |  | libusbx-1.0.22-2.oe1.aarch64 | Subject to modification |
| libxkbcommon-0.8.4-3.oe1.src |  | libxkbcommon-0.8.4-3.oe1.aarch64  | Subject to modification |
| libxkbcommon-0.8.4-3.oe1.src |  | libxkbcommon-x11-0.8.4-3.oe1.aarch64 | Subject to modification |
| gpgme-1.13.1-5.oe1.src |  | cpp-gpgme-1.13.1-5.oe1.aarch64  | Subject to modification |
| gpgme-1.13.1-5.oe1.src |  | gpgme-1.13.1-5.oe1.aarch64  | Subject to modification |
| gpgme-1.13.1-5.oe1.src |  | qt-gpgme-1.13.1-5.oe1.aarch64 | Subject to modification |
| dbus-python-1.2.8-8.oe1.src | leve3 |  |  |
| libldb-2.0.8-2.oe1.src | leve2 | libldb-2.0.8-2.oe1.aarch64  |  |
| libldb-2.0.8-2.oe1.src | leve2 | python3-ldb-2.0.8-2.oe1.aarch64 |  |
| emacs-26.1-12.oe1.src | leve3 | emacs-26.1-12.oe1.aarch64 |  |
| libgudev-233-1.oe1.src | leve2 | libgudev-233-1.oe1.aarch64 |  |
| numactl-2.0.13-4.oe1.src | leve3 | numactl-libs-2.0.13-4.oe1.aarch64 |  |
| NetworkManager-1.16.0-7.oe1.src | leve2 | NetworkManager-1.16.0-7.oe1.aarch64  |  |
| NetworkManager-1.16.0-7.oe1.src | leve2 | NetworkManager-libnm-1.16.0-7.oe1.aarch64  |  |
| NetworkManager-1.16.0-7.oe1.src | leve2 | NetworkManager-wwan-1.16.0-7.oe1.aarch64 |  |
| gstreamer1-plugins-base-1.14.4-3.oe1.src |  | gstreamer1-plugins-base-1.14.4-3.oe1.aarch64 | Subject to modification |
| libogg-1.3.3-3.oe1.src |  | libogg-1.3.3-3.oe1.aarch64 | Subject to modification |
| libssh-0.8.3-7.oe1.src | level1 | libssh-0.8.3-7.oe1.aarch64 |  |
| libssh2-1.9.0-2.oe1.src | level1 | libssh2-1.9.0-2.oe1.aarch64 |  |
| bpg-fonts-20120413-12.oe1.src | level3 |  |  |
| file-5.38-1.oe1.src | level3 | file-libs-5.38-1.oe1.aarch64 |  |
| json-c-0.13.1-6.oe1.src | leve2 | json-c-0.13.1-6.oe1.aarch64 |  |
| libXinerama-1.1.4-5.oe1.src |  | libXinerama-1.1.4-5.oe1.aarch64 | Subject to modification |
| libXmu-1.1.3-1.oe1.src |  | libXmu-1.1.3-1.oe1.aarch64 | Subject to modification |
| libarchive-3.4.1-2.oe1.src | level2 | libarchive-3.4.1-2.oe1.aarch64 |  |
| libdbusmenu-16.04.0-10.oe1.src |  | libdbusmenu-16.04.0-10.oe1.aarch64  | Subject to modification |
| libdbusmenu-16.04.0-10.oe1.src |  | libdbusmenu-gtk2-16.04.0-10.oe1.aarch64  | Subject to modification |
| libdbusmenu-16.04.0-10.oe1.src |  | libdbusmenu-gtk3-16.04.0-10.oe1.aarch64  | Subject to modification |
| libdbusmenu-16.04.0-10.oe1.src |  | libdbusmenu-jsonloader-16.04.0-10.oe1.aarch64 | Subject to modification |
| libsecret-0.18.6-4.oe1.src | level2 | libsecret-0.18.6-4.oe1.aarch64 |  |
| mariadb-connector-c-3.0.6-6.oe1.src | level2 | mariadb-connector-c-3.0.6-6.oe1.aarch64 |  |
| perl-Socket-2.029-2.oe1.src | level3 |  |  |
| policycoreutils-2.8-14.oe1.src | level3 |  |  |
| python-requests-2.21.0-5.oe1.src | level3 |  |  |
| gobject-introspection-1.58.0-6.oe1.src | level2 | gobject-introspection-1.58.0-6.oe1.aarch64 |  |
| libaio-0.3.111-5.oe1.src | level2 | libaio-0.3.111-5.oe1.aarch64 |  |
| libtirpc-1.1.4-1.oe1.src | level2 | libtirpc-1.1.4-1.oe1.aarch64 |  |
| p11-kit-0.23.14-6.oe1.src | level3 | p11-kit-0.23.14-6.oe1.aarch64 |  |
| perl-Text-ParseWords-3.30-419.oe1.src | level3 |  |  |
| poppler-0.67.0-5.oe1.src | level3 | poppler-0.67.0-5.oe1.aarch64  |  |
| poppler-0.67.0-5.oe1.src | level3 | poppler-cpp-0.67.0-5.oe1.aarch64  |  |
| poppler-0.67.0-5.oe1.src | level3 | poppler-glib-0.67.0-5.oe1.aarch64  |  |
| poppler-0.67.0-5.oe1.src | level3 | poppler-qt-0.67.0-5.oe1.aarch64  |  |
| poppler-0.67.0-5.oe1.src | level3 | poppler-qt5-0.67.0-5.oe1.aarch64 |  |
| tcl-8.6.8-8.oe1.src | level3 | tcl-8.6.8-8.oe1.aarch64 |  |
| kernel-4.19.90-2003.4.0.0036.oe1.src | level3 | kernel-4.19.90-2003.4.0.0036.oe1.aarch64  | Subject to modification |
| kernel-4.19.90-2003.4.0.0036.oe1.src | level3 | kernel-devel-4.19.90-2003.4.0.0036.oe1.aarch64  |  |
| kernel-4.19.90-2003.4.0.0036.oe1.src | level3 | kernel-tools-4.19.90-2003.4.0.0036.oe1.aarch64 |  |
| dracut-049-8.oe1.src | level3 |  |  |
| httpd-2.4.34-15.oe1.src | level3 |  |  |
| libwebp-1.0.0-5.oe1.src |  | libwebp-1.0.0-5.oe1.aarch64  | Subject to modification |
| libwebp-1.0.0-5.oe1.src |  | libwebp-java-1.0.0-5.oe1.aarch64 | Subject to modification |
| perl-Digest-MD5-2.55-419.oe1.src | level3 |  |  |
| sssd-2.2.2-3.oe1.src | level2 | sssd-2.2.2-3.oe1.aarch64 |  |
| findutils-4.7.0-4.oe1.src | level3 |  |  |
| libXft-2.3.2-13.oe1.src |  | libXft-2.3.2-13.oe1.aarch64 | Subject to modification |
| nbdkit-1.6.2-2.oe1.src | level3 | nbdkit-ocaml-plugin-1.6.2-2.oe1.aarch64 |  |
| qt-4.8.7-47.oe1.src |  | qt-4.8.7-47.oe1.aarch64  | Subject to modification |
| qt-4.8.7-47.oe1.src |  | qt-devel-4.8.7-47.oe1.aarch64 | Subject to modification |
| atf-0.20-13.oe1.src | level3 | libatf-c++-0.20-13.oe1.aarch64  |  |
| atf-0.20-13.oe1.src | level3 | libatf-c-0.20-13.oe1.aarch64 |  |
| dbus-glib-0.110-5.oe1.src | level3 | dbus-glib-0.110-5.oe1.aarch64 |  |
| keyutils-1.5.10-11.oe1.src | level3 | keyutils-libs-1.5.10-11.oe1.aarch64 |  |
| libXpm-3.5.12-11.oe1.src |  | libXpm-3.5.12-11.oe1.aarch64 | Subject to modification |
| mariadb-10.3.9-8.oe1.src | level3 | mariadb-embedded-10.3.9-8.oe1.aarch64  |  |
| mariadb-10.3.9-8.oe1.src | level3 | mariadb-server-10.3.9-8.oe1.aarch64 |  |
| mpfr-3.1.6-3.oe1.src | level3 | mpfr-3.1.6-3.oe1.aarch64 |  |
| qt5-qttools-5.11.1-4.oe1.src | level3 | qt5-qttools-5.11.1-4.oe1.aarch64  |  |
| qt5-qttools-5.11.1-4.oe1.src | level3 | qt5-qttools-devel-5.11.1-4.oe1.aarch64 |  |
| webkit2gtk3-2.22.2-6.oe1.src | level3 | webkit2gtk3-2.22.2-6.oe1.aarch64  |  |
| webkit2gtk3-2.22.2-6.oe1.src | level3 | webkit2gtk3-jsc-2.22.2-6.oe1.aarch64 |  |
| lz4-1.9.2-2.oe1.src | level3 | lz4-1.9.2-2.oe1.aarch64 |  |
| net-snmp-5.8-7.oe1.src | level3 | net-snmp-5.8-7.oe1.aarch64  |  |
| net-snmp-5.8-7.oe1.src | level3 | net-snmp-libs-5.8-7.oe1.aarch64 |  |
| perl-parent-0.237-4.oe1.src | level3 |  |  |
| satyr-0.27-5.oe1.src | level3 | satyr-0.27-5.oe1.aarch64 |  |
| cryptsetup-2.0.4-2.oe1.src | level3 | cryptsetup-2.0.4-2.oe1.aarch64 |  |
| filesystem-3.9-3.oe1.src | level3 |  |  |
| git-2.23.0-12.oe1.src | level3 |  |  |
| ibus-1.5.19-7.oe1.src | level3 | ibus-libs-1.5.19-7.oe1.aarch64 |  |
| libXcursor-1.1.15-5.oe1.src | level3 | libXcursor-1.1.15-5.oe1.aarch64 |  |
| pixman-0.38.0-1.oe1.src | level3 | pixman-0.38.0-1.oe1.aarch64 |  |
| rdma-core-20.1-6.oe1.src | level3 | rdma-core-20.1-6.oe1.aarch64 |  |
| attr-2.4.48-8.oe1.src | level3 | attr-2.4.48-8.oe1.aarch64 |  |
| gdbm-1.18-2.oe1.src | level3 | gdbm-1.18-2.oe1.aarch64 |  |
| gnome-desktop3-3.30.1-3.oe1.src | level3 | gnome-desktop3-3.30.1-3.oe1.aarch64 |  |
| hicolor-icon-theme-0.17-4.oe1.src | level3 |  |  |
| libmnl-1.0.4-10.oe1.src | level2 | libmnl-1.0.4-10.oe1.aarch64 |  |
| libnotify-0.7.8-1.oe1.src | level3 | libnotify-0.7.8-1.oe1.aarch64 |  |
| libvorbis-1.3.6-5.oe1.src | level3 | libvorbis-1.3.6-5.oe1.aarch64 |  |
| sane-backends-1.0.28-6.oe1.src | level3 | sane-backends-libs-1.0.28-6.oe1.aarch64 |  |
| systemtap-4.1-3.oe1.src | level3 |  |  |
| tar-1.30-11.oe1.src | level3 |  |  |
| xorg-x11-server-1.20.6-4.oe1.src | level3 | xorg-x11-server-1.20.6-4.oe1.aarch64 |  |
| GConf2-3.2.6-24.oe1.src | level3 | GConf2-3.2.6-24.oe1.aarch64 |  |
| apr-1.6.5-4.oe1.src | level3 | apr-1.6.5-4.oe1.aarch64 |  |
| apr-util-1.6.1-11.oe1.src | level3 | apr-util-1.6.1-11.oe1.aarch64 |  |
| ca-certificates-2020.2.40-1.oe1.src | level3 |  |  |
| ghostscript-9.27-8.oe1.src | level3 | ghostscript-9.27-8.oe1.aarch64 |  |
| gsettings-desktop-schemas-3.34.0-1.oe1.src | level3 |  |  |
| iproute-5.4.0-2.oe1.src | level3 |  |  |
| iptables-1.8.1-4.oe1.src | level3 | iptables-1.8.1-4.oe1.aarch64  |  |
| iptables-1.8.1-4.oe1.src | level3 | iptables-libs-1.8.1-4.oe1.aarch64 |  |
| libXtst-1.2.3-10.oe1.src | level3 | libXtst-1.2.3-10.oe1.aarch64 |  |
| libseccomp-2.4.1-3.oe1.src | level2 | libseccomp-2.4.1-3.oe1.aarch64 |  |
| perl-Time-Local-1.280-6.oe1.src | level3 |  |  |
| zstd-1.3.6-3.oe1.src | level3 | zstd-1.3.6-3.oe1.aarch64 |  |
| dnf-4.2.15-8.oe1.src | level3 |  |  |
| fuse-2.9.9-0.oe1.src | level3 | fuse-2.9.9-0.oe1.aarch64  |  |
| fuse-2.9.9-0.oe1.src | level3 | fuse3-3.9.0-0.oe1.aarch64 |  |
| gd-2.2.5-6.oe1.src | level3 | gd-2.2.5-6.oe1.aarch64 |  |
| gperftools-2.7-7.oe1.src | level3 | gperftools-libs-2.7-7.oe1.aarch64 |  |
| libevent-2.1.11-2.oe1.src | level2 | libevent-2.1.11-2.oe1.aarch64 |  |
| libguestfs-1.40.2-6.oe1.src | level2 | python2-libguestfs-1.40.2-6.oe1.aarch64 |  |
| perl-Storable-3.15-2.oe1.src | level3 |  |  |
| python-cryptography-2.6.1-1.oe1.src | level3 |  |  |
| which-2.21-14.oe1.src | level3 |  |  |
| automake-1.16.1-6.oe1.src | level3 |  |  |
| binutils-2.33.1-5.oe1.src | level3 | binutils-2.33.1-5.oe1.aarch64 |  |
| clutter-1.26.2-9.oe1.src | level3 | clutter-1.26.2-9.oe1.aarch64 |  |
| cpio-2.12-14.oe1.src | level3 |  |  |
| graphviz-2.40.1-38.oe1.src | level3 | graphviz-2.40.1-38.oe1.aarch64  |  |
| graphviz-2.40.1-38.oe1.src | level3 | graphviz-java-2.40.1-38.oe1.aarch64  |  |
| graphviz-2.40.1-38.oe1.src | level3 | graphviz-lua-2.40.1-38.oe1.aarch64  |  |
| graphviz-2.40.1-38.oe1.src | level3 | graphviz-ocaml-2.40.1-38.oe1.aarch64  |  |
| graphviz-2.40.1-38.oe1.src | level3 | graphviz-perl-2.40.1-38.oe1.aarch64  |  |
| graphviz-2.40.1-38.oe1.src | level3 | graphviz-python2-2.40.1-38.oe1.aarch64  |  |
| graphviz-2.40.1-38.oe1.src | level3 | graphviz-python3-2.40.1-38.oe1.aarch64  |  |
| graphviz-2.40.1-38.oe1.src | level3 | graphviz-ruby-2.40.1-38.oe1.aarch64  |  |
| graphviz-2.40.1-38.oe1.src | level3 | graphviz-tcl-2.40.1-38.oe1.aarch64 |  |
| gzip-1.9-18.oe1.src | level3 |  |  |
| libpcap-1.9.1-4.oe1.src | level2 | libpcap-1.9.1-4.oe1.aarch64 |  |
| libpwquality-1.4.0-11.oe1.src | level3 | libpwquality-1.4.0-11.oe1.aarch64 |  |
| librsvg2-2.44.15-1.oe1.src | level3 | librsvg2-2.44.15-1.oe1.aarch64 |  |
| lzo-2.10-1.oe1.src | level3 | lzo-2.10-1.oe1.aarch64  |  |
| lzo-2.10-1.oe1.src | level3 | lzo-minilzo-2.10-1.oe1.aarch64 |  |
| nginx-1.16.1-2.oe1.src | level3 |  |  |
| orc-0.4.28-5.oe1.src | level3 | orc-0.4.28-5.oe1.aarch64 |  |
| pcre2-10.33-2.oe1.src | level3 | pcre2-10.33-2.oe1.aarch64 |  |
| perl-MIME-Base64-3.15-418.oe1.src | level3 |  |  |
| sgml-common-0.6.3-51.oe1.src | level3 |  |  |
| xorg-x11-font-utils-7.5-42.oe1.src | level3 |  |  |
| ModemManager-1.8.0-7.oe1.src | level3 | ModemManager-devel-1.8.0-7.oe1.aarch64  |  |
| ModemManager-1.8.0-7.oe1.src | level3 | ModemManager-glib-1.8.0-7.oe1.aarch64 |  |
| anaconda-29.24.7-28.oe1.src | level3 | anaconda-29.24.7-28.oe1.aarch64 |  |
| rust-1.29.1-3.oe1.src | level3 |  |  |
| clamav-0.101.4-5.oe1.src | level3 | clamav-0.101.4-5.oe1.aarch64 |  |
| cogl-1.22.4-2.oe1.src | level3 | cogl-1.22.4-2.oe1.aarch64 |  |
| colord-1.4.3-6.oe1.src | level3 | colord-1.4.3-6.oe1.aarch64  |  |
| colord-1.4.3-6.oe1.src | level3 | colord-libs-1.4.3-6.oe1.aarch64 |  |
| desktop-file-utils-0.24-1.oe1.src | level3 |  |  |
| fftw-3.3.8-6.oe1.src | level3 | fftw-libs-double-3.3.8-6.oe1.aarch64  |  |
| fftw-3.3.8-6.oe1.src | level3 | fftw-libs-long-3.3.8-6.oe1.aarch64  |  |
| fftw-3.3.8-6.oe1.src | level3 | fftw-libs-single-3.3.8-6.oe1.aarch64 |  |
| glibmm24-2.58.0-2.oe1.src | level3 | glibmm24-2.58.0-2.oe1.aarch64 |  |
| gtk-vnc-0.9.0-4.oe1.src | level3 | gtk-vnc2-0.9.0-4.oe1.aarch64  |  |
| gtk-vnc-0.9.0-4.oe1.src | level3 | gvnc-0.9.0-4.oe1.aarch64  |  |
| gtk-vnc-0.9.0-4.oe1.src | level3 | gvncpulse-0.9.0-4.oe1.aarch64 |  |
| harfbuzz-1.8.7-2.oe1.src | level3 | harfbuzz-1.8.7-2.oe1.aarch64 |  |
| libXaw-1.0.13-13.oe1.src |  | libXaw-1.0.13-13.oe1.aarch64 | Subject to modification |
| libidn2-2.0.5-8.oe1.src | level2 | libidn2-2.0.5-8.oe1.aarch64 |  |
| libsigc++20-2.10.1-2.oe1.src | level3 | libsigc++20-2.10.1-2.oe1.aarch64 |  |
| libsndfile-1.0.28-18.oe1.src | level3 | libsndfile-1.0.28-18.oe1.aarch64 |  |
| udisks2-2.8.1-4.oe1.src | level3 | libudisks2-2.8.1-4.oe1.aarch64  |  |
| udisks2-2.8.1-4.oe1.src | level3 | udisks2-lsm-2.8.1-4.oe1.aarch64  |  |
| udisks2-2.8.1-4.oe1.src | level3 | udisks2-lvm2-2.8.1-4.oe1.aarch64  |  |
| udisks2-2.8.1-4.oe1.src | level3 | udisks2-vdo-2.8.1-4.oe1.aarch64  |  |
| udisks2-2.8.1-4.oe1.src | level3 | udisks2-zram-2.8.1-4.oe1.aarch64 |  |
| llvm-7.0.0-9.oe1.src | level3 |  |  |
| parted-3.3-2.oe1.src | level3 | parted-3.3-2.oe1.aarch64 |  |
| perl-ExtUtils-MakeMaker-7.42-2.oe1.src | level3 |  |  |
| perl-Test-Simple-1.302140-3.oe1.src | level3 |  |  |
| perl-URI-1.76-3.oe1.src | level3 |  |  |
| perl-libnet-3.11-420.oe1.src | level3 |  |  |
| procps-ng-3.3.16-11.oe1.src | level3 | procps-ng-3.3.16-11.oe1.aarch64 |  |
| python-qt5-5.11.2-6.oe1.src | level3 |  |  |
| numpy-1.16.5-2.oe1.src | level3 |  |  |
| qt5-qtwebchannel-5.11.1-4.oe1.src | level3 | qt5-qtwebchannel-5.11.1-4.oe1.aarch64 |  |
| snappy-1.1.7-10.oe1.src | level3 | snappy-1.1.7-10.oe1.aarch64 |  |
| xorg-x11-server-utils-7.7-28.oe1.src | level3 |  |  |
| ORBit2-2.14.19-23.oe1.src | level3 | ORBit2-2.14.19-23.oe1.aarch64 |  |
| subscription-manager-1.23.3-5.oe1.src | level3 |  |  |
| gcr-3.34.0-1.oe1.src | level3 | gcr-3.34.0-1.oe1.aarch64 |  |
| gettext-0.20.1-2.oe1.src | level3 | gettext-0.20.1-2.oe1.aarch64  |  |
| gettext-0.20.1-2.oe1.src | level3 | gettext-devel-0.20.1-2.oe1.aarch64 |  |
| libXau-1.0.9-2.oe1.src |  | libXau-1.0.9-2.oe1.aarch64 | Subject to modification |
| libXv-1.0.11-11.oe1.src | level3 | libXv-1.0.11-11.oe1.aarch64 |  |
| libXxf86vm-1.1.4-13.oe1.src | level3 | libXxf86vm-1.1.4-13.oe1.aarch64 |  |
| libart_lgpl-2.3.21-23.oe1.src | level3 | libart_lgpl-2.3.21-23.oe1.aarch64 |  |
| libdnf-0.37.2-2.oe1.src | level2 | libdnf-0.37.2-2.oe1.aarch64 |  |
| nettle-3.4.1rc1-4.oe1.src | level3 | nettle-3.4.1rc1-4.oe1.aarch64 |  |
| openEuler-rpm-config-30-9.oe1.src | level3 |  |  |
| perl-Math-BigInt-1.9998.13-5.oe1.src | level3 |  |  |
| perl-Text-Tabs+Wrap-2013.0523-419.oe1.src | level3 |  |  |
| perl-XML-Parser-2.44-3.oe1.src | level3 |  |  |
| psmisc-23.1-5.oe1.src | level3 |  |  |
| pytest-3.6.4-3.oe1.src | level3 |  |  |
| python-dateutil-2.7.0-7.oe1.src | level3 |  |  |
| qt5-qtlocation-5.11.1-5.oe1.src | level3 | qt5-qtlocation-5.11.1-5.oe1.aarch64 |  |
| setup-2.13.3-4.oe1.src | level3 |  |  |
| shared-mime-info-1.10-4.oe1.src | level3 |  |  |
| tk-8.6.8-4.oe1.src | level3 | tk-8.6.8-4.oe1.aarch64 |  |
| cdrkit-1.1.11-43.oe1.src | level3 | libusal-1.1.11-43.oe1.aarch64 |  |
| freeradius-3.0.15-17.oe1.src | level3 | freeradius-3.0.15-17.oe1.aarch64 |  |
| hivex-1.3.17-2.oe1.src | level3 | python2-hivex-1.3.17-2.oe1.aarch64 |  |
| hwdata-0.316-3.oe1.src | level3 |  |  |
| java-1.8.0-openjdk-1.8.0.242.b08-1.h5.oe1.src | level3 | java-1.8.0-openjdk-1.8.0.242.b08-1.h5.oe1.aarch64  |  |
| java-1.8.0-openjdk-1.8.0.242.b08-1.h5.oe1.src | level3 | java-1.8.0-openjdk-demo-1.8.0.242.b08-1.h5.oe1.aarch64  |  |
| java-1.8.0-openjdk-1.8.0.242.b08-1.h5.oe1.src | level3 | java-1.8.0-openjdk-devel-1.8.0.242.b08-1.h5.oe1.aarch64  |  |
| java-1.8.0-openjdk-1.8.0.242.b08-1.h5.oe1.src | level3 | java-1.8.0-openjdk-headless-1.8.0.242.b08-1.h5.oe1.aarch64 |  |
| libassuan-2.5.1-6.oe1.src | level3 | libassuan-2.5.1-6.oe1.aarch64 |  |
| libblockdev-2.20-4.oe1.src | level3 | libblockdev-2.20-4.oe1.aarch64 |  |
| libedit-3.1-26.oe1.src | level3 | libedit-3.1-26.oe1.aarch64 |  |
| libglade2-2.6.4-21.oe1.src | level3 | libglade2-2.6.4-21.oe1.aarch64  |  |
| libglade2-2.6.4-21.oe1.src | level3 | libglade2-devel-2.6.4-21.oe1.aarch64 |  |
| libinput-1.14.1-2.oe1.src | level3 | libinput-1.14.1-2.oe1.aarch64 |  |
| network-manager-applet-1.8.22-2.oe1.src | level3 | libnma-1.8.22-2.oe1.aarch64  |  |
| network-manager-applet-1.8.22-2.oe1.src | level3 | nm-connection-editor-1.8.22-2.oe1.aarch64 |  |
| librepo-1.11.0-2.oe1.src | level2 | librepo-1.11.0-2.oe1.aarch64 |  |
| libsolv-0.7.7-2.oe1.src | level2 | libsolv-0.7.7-2.oe1.aarch64 |  |
| libstoragemgmt-1.8.0-3.oe1.src | level3 | libstoragemgmt-1.8.0-3.oe1.aarch64 |  |
| libtasn1-4.13-7.oe1.src | level3 | libtasn1-4.13-7.oe1.aarch64 |  |
| libxshmfence-1.3-6.oe1.src | level3 | libxshmfence-1.3-6.oe1.aarch64 |  |
| logrotate-3.15.1-2.oe1.src | level3 |  |  |
| make-4.2.1-15.oe1.src | level3 |  |  |
| perl-Time-HiRes-1.9760-2.oe1.src | level3 |  |  |
| perl-libwww-perl-6.35-2.oe1.src | level3 |  |  |
| perl-version-0.99.24-3.oe1.src | level3 |  |  |
| python-sphinx-1.7.6-6.oe1.src | level3 |  |  |
| sip-4.19.12-10.oe1.src | level3 |  |  |
| rsyslog-8.1907.0-5.oe1.src | level3 |  |  |
| startup-notification-0.12-17.oe1.src | level3 | startup-notification-0.12-17.oe1.aarch64 |  |
| uuid-1.6.2-43.oe1.src | level3 | uuid-1.6.2-43.oe1.aarch64  |  |
| uuid-1.6.2-43.oe1.src | level3 | uuid-c++-1.6.2-43.oe1.aarch64  |  |
| uuid-1.6.2-43.oe1.src | level3 | uuid-dce-1.6.2-43.oe1.aarch64 |  |
| bluez-5.50-7.oe1.src | level3 | bluez-libs-5.50-7.oe1.aarch64 |  |
| brltty-5.6-35.oe1.src | level3 | brltty-5.6-35.oe1.aarch64  |  |
| brltty-5.6-35.oe1.src | level3 | brltty-at-spi2-5.6-35.oe1.aarch64  |  |
| brltty-5.6-35.oe1.src | level3 | brltty-xw-5.6-35.oe1.aarch64 |  |
| c-ares-1.15.0-1.oe1.src | level3 | c-ares-1.15.0-1.oe1.aarch64 |  |
| diffutils-3.7-3.oe1.src | level3 |  |  |
| enchant-1.6.1-2.oe1.src | level3 | enchant-1.6.1-2.oe1.aarch64  |  |
| enchant-1.6.1-2.oe1.src | level3 | enchant-aspell-1.6.1-2.oe1.aarch64  |  |
| enchant-1.6.1-2.oe1.src | level3 | enchant-voikko-1.6.1-2.oe1.aarch64 |  |
| gnome-online-accounts-3.30.0-5.oe1.src | level3 | gnome-online-accounts-3.30.0-5.oe1.aarch64 |  |
| gnu-free-fonts-20120503-21.oe1.src | level3 |  |  |
| gnupg2-2.2.17-5.oe1.src | level3 |  |  |
| gpm-1.20.7-21.oe1.src | level3 | gpm-libs-1.20.7-21.oe1.aarch64 |  |
| iso-codes-3.79-4.oe1.src | level3 |  |  |
| multipath-tools-0.7.7-17.oe1.src | level3 | multipath-tools-0.7.7-17.oe1.aarch64 |  |
| libepoxy-1.5.3-2.oe1.src | level3 | libepoxy-1.5.3-2.oe1.aarch64 |  |
| libmpc-1.1.0-3.oe1.src | level3 | libmpc-1.1.0-3.oe1.aarch64 |  |
| libpciaccess-0.14-3.oe1.src | level3 | libpciaccess-0.14-3.oe1.aarch64 |  |
| libsepol-2.9-1.oe1.src | level3 | libsepol-2.9-1.oe1.aarch64 |  |
| openwsman-2.6.5-12.oe1.src | level3 | libwsman1-2.6.5-12.oe1.aarch64  |  |
| openwsman-2.6.5-12.oe1.src | level3 | openwsman-client-2.6.5-12.oe1.aarch64  |  |
| openwsman-2.6.5-12.oe1.src | level3 | openwsman-server-2.6.5-12.oe1.aarch64 |  |
| mesa-libGLU-9.0.1-1.oe1.src |  | mesa-libGLU-9.0.1-1.oe1.aarch64 | Subject to modification |
| openssh-7.8p1-8.oe1.src | level3 |  |  |
| perl-Module-Install-1.19-6.oe1.src | level3 |  |  |
| perl-Term-ANSIColor-4.06-511.oe1.src | level3 |  |  |
| perl-podlators-4.11-5.oe1.src | level3 |  |  |
| protobuf-3.9.0-7.oe1.src | level3 | protobuf-3.9.0-7.oe1.aarch64  |  |
| protobuf-3.9.0-7.oe1.src | level3 | protobuf-lite-3.9.0-7.oe1.aarch64 |  |
| python-jinja2-2.10-10.oe1.src | level3 |  |  |
| pycairo-1.18.2-1.oe1.src | level3 |  |  |
| python-idna-2.8-3.oe1.src | level3 |  |  |
| python-mock-2.0.0-11.oe1.src | level3 |  |  |
| python-pbr-4.1.1-3.oe1.src | level3 |  |  |
| rrdtool-1.7.0-18.oe1.src | level3 | rrdtool-1.7.0-18.oe1.aarch64 |  |
| speech-dispatcher-0.8.8-9.oe1.src | level3 | speech-dispatcher-0.8.8-9.oe1.aarch64 |  |
| xfsprogs-4.17.0-5.oe1.src | level3 | xfsprogs-4.17.0-5.oe1.aarch64 |  |
| accountsservice-0.6.54-2.oe1.src | level3 | accountsservice-libs-0.6.54-2.oe1.aarch64 |  |
| argon2-20161029-9.oe1.src | level3 | libargon2-20161029-9.oe1.aarch64 |  |
| augeas-1.12.0-4.oe1.src | level3 | augeas-1.12.0-4.oe1.aarch64 |  |
| babl-0.1.56-3.oe1.src | level3 | babl-0.1.56-3.oe1.aarch64 |  |
| cracklib-2.9.7-2.oe1.src | level3 | cracklib-2.9.7-2.oe1.aarch64 |  |
| ndctl-63-2.oe1.src | level3 | daxctl-libs-63-2.oe1.aarch64  |  |
| ndctl-63-2.oe1.src | level3 | ndctl-libs-63-2.oe1.aarch64 |  |
| dejavu-fonts-2.35-8.oe1.src | level3 |  |  |
| efivar-37-3.oe1.src | level3 | efivar-libs-37-3.oe1.aarch64 |  |
| evolution-data-server-3.30.1-3.oe1.src | level3 | evolution-data-server-3.30.1-3.oe1.aarch64 |  |
| flac-1.3.2-12.oe1.src | level3 | flac-1.3.2-12.oe1.aarch64  |  |
| flac-1.3.2-12.oe1.src | level3 | xmms-flac-1.3.2-12.oe1.aarch64 |  |
| gegl04-0.4.8-3.oe1.src | level3 | gegl04-0.4.8-3.oe1.aarch64 |  |
| grub2-2.02-73.oe1.src | level3 |  |  |
| liblouis-3.7.0-2.oe1.src | level3 | liblouis-3.7.0-2.oe1.aarch64 |  |
| libnfnetlink-1.0.1-15.oe1.src | level2 | libnfnetlink-1.0.1-15.oe1.aarch64 |  |
| libproxy-0.4.15-13.oe1.src | level2 | libproxy-0.4.15-13.oe1.aarch64 |  |
| libsemanage-2.9-2.oe1.src | level3 | libsemanage-2.9-2.oe1.aarch64 |  |
| libxkbfile-1.1.0-2.oe1.src | level3 | libxkbfile-1.1.0-2.oe1.aarch64 |  |
| libyaml-0.2.2-2.oe1.src | level2 | libyaml-0.2.2-2.oe1.aarch64 |  |
| ocaml-4.07.0-6.oe1.src | level3 | ocaml-4.07.0-6.oe1.aarch64 |  |
| openblas-0.3.3-3.oe1.src | level3 | openblas-0.3.3-3.oe1.aarch64 |  |
| opus-1.3.1-1.oe1.src | level3 | opus-1.3.1-1.oe1.aarch64 |  |
| perl-TimeDate-2.30-17.oe1.src | level3 |  |  |
| pygtk2-2.24.0-24.oe1.src | level3 |  |  |
| python-pillow-5.3.0-4.oe1.src | level3 |  |  |
| qemu-4.0.1-11.oe1.src | level3 |  |  |
| qt5-qtxmlpatterns-5.11.1-4.oe1.src | level3 | qt5-qtxmlpatterns-5.11.1-4.oe1.aarch64 |  |
| slang-2.3.2-8.oe1.src | level3 | slang-2.3.2-8.oe1.aarch64 |  |
| vim-8.1.450-8.oe1.src | level3 | vim-X11-8.1.450-8.oe1.aarch64 |  |
| xdg-utils-1.1.3-4.oe1.src | level3 |  |  |
| GeoIP-1.6.12-5.oe1.src | level3 | GeoIP-1.6.12-5.oe1.aarch64 |  |
| ImageMagick-6.9.10.67-6.oe1.src | level3 | ImageMagick-6.9.10.67-6.oe1.aarch64  |  |
| ImageMagick-6.9.10.67-6.oe1.src | level3 | ImageMagick-c++-6.9.10.67-6.oe1.aarch64 |  |
| clang-7.0.0-5.oe1.src | level3 |  |  |
| crontabs-1.11-21.oe1.src | level3 |  |  |
| dconf-0.34.0-1.oe1.src | level3 | dconf-0.34.0-1.oe1.aarch64 |  |
| ding-libs-0.6.1-42.oe1.src | level2 | ding-libs-0.6.1-42.oe1.aarch64 |  |
| docbook-dtds-1.0-78.oe1.src | level3 |  |  |
| enchant2-2.2.3-7.oe1.src | level3 | enchant2-2.2.3-7.oe1.aarch64 |  |
| glib-1.2.10-55.oe1.src |  | glib-1.2.10-55.oe1.aarch64 | Subject to modification |
| gnome-vfs2-2.24.4-30.oe1.src | level3 | gnome-vfs2-2.24.4-30.oe1.aarch64 |  |
| google-droid-fonts-20120715-15.oe1.src | level3 |  |  |
| ilmbase-2.2.0-16.oe1.src | level3 | ilmbase-2.2.0-16.oe1.aarch64 |  |
| initscripts-10.01-6.oe1.src | level3 |  |  |
| langtable-0.0.38-8.h7.oe1.src | level3 |  |  |
| ldns-1.7.0-25.oe1.src | level3 | ldns-1.7.0-25.oe1.aarch64 |  |
| libbonobo-2.32.1-19.oe1.src | level3 | libbonobo-2.32.1-19.oe1.aarch64 |  |
| mongo-c-driver-1.13.1-3.oe1.src | level3 | libbson-1.13.1-3.oe1.aarch64  |  |
| mongo-c-driver-1.13.1-3.oe1.src | level3 | mongo-c-driver-1.13.1-3.oe1.aarch64 |  |
| libexif-0.6.21-20.oe1.src | level3 | libexif-0.6.21-20.oe1.aarch64 |  |
| libfastjson-0.99.8-3.oe1.src | level2 | libfastjson-0.99.8-3.oe1.aarch64 |  |
| libgexiv2-0.10.8-5.oe1.src | level3 | libgexiv2-0.10.8-5.oe1.aarch64 |  |
| libgnome-2.32.1-20.oe1.src | level3 | libgnome-2.32.1-20.oe1.aarch64 |  |
| libgnomecanvas-2.30.3-19.oe1.src | level3 | libgnomecanvas-2.30.3-19.oe1.aarch64 |  |
| libgweather-3.32.2-3.oe1.src | level3 | libgweather-3.32.2-3.oe1.aarch64 |  |
| libmodulemd-1.6.4-4.oe1.src | level1 | libmodulemd-1.6.4-4.oe1.aarch64 |  |
| libplist-2.0.0-13.oe1.src | level3 | libplist-2.0.0-13.oe1.aarch64 |  |
| libthai-0.1.28-3.oe1.src | level3 | libthai-0.1.28-3.oe1.aarch64 |  |
| libtheora-1.1.1-24.oe1.src | level3 | libtheora-1.1.1-24.oe1.aarch64 |  |
| libunistring-0.9.10-7.oe1.src | level3 | libunistring-0.9.10-7.oe1.aarch64 |  |
| libwacom-0.31-2.oe1.src | level3 | libwacom-0.31-2.oe1.aarch64 |  |
| openjpeg2-2.3.1-2.oe1.src | level3 | openjpeg2-2.3.1-2.oe1.aarch64 |  |
| perl-DBI-1.642-2.oe1.src | level3 |  |  |
| perl-HTTP-Message-6.18-4.oe1.src | level3 |  |  |
| perl-Pod-Simple-3.35-418.oe1.src | level3 |  |  |
| subversion-1.10.6-2.oe1.src | level3 | ruby-subversion-1.10.6-2.oe1.aarch64 |  |
| pyOpenSSL-19.0.0-1.oe1.src | level3 |  |  |
| python-testtools-2.3.0-11.oe1.src | level3 |  |  |
| pytz-2019.2-2.oe1.src | level3 |  |  |
| pyyaml-5.1.2-1.oe1.src | level3 |  |  |
| unbound-1.7.3-14.oe1.src | level3 | unbound-libs-1.7.3-14.oe1.aarch64 |  |
| qt5-qtserialport-5.11.1-4.oe1.src | level3 | qt5-qtserialport-5.11.1-4.oe1.aarch64 |  |
| qt5-qtsvg-5.11.1-4.oe1.src | level3 | qt5-qtsvg-5.11.1-4.oe1.aarch64 |  |
| qt5-qtwebsockets-5.11.1-5.oe1.src | level3 | qt5-qtwebsockets-5.11.1-5.oe1.aarch64 |  |
| rest-0.8.1-7.oe1.src | level3 | rest-0.8.1-7.oe1.aarch64 |  |
| stix-fonts-1.1.0-15.oe1.src | level3 |  |  |
| telepathy-glib-0.24.1-11.oe1.src | level3 | telepathy-glib-0.24.1-11.oe1.aarch64 |  |
| unixODBC-2.3.7-2.oe1.src | level3 | unixODBC-2.3.7-2.oe1.aarch64 |  |
| unzip-6.0-45.oe1.src | level3 |  |  |
| upower-0.99.8-5.oe1.src | level3 | upower-0.99.8-5.oe1.aarch64 |  |
| xorg-x11-xinit-1.4.0-5.oe1.src | level3 |  |  |
| OpenEXR-2.2.0-17.oe1.src | level3 | OpenEXR-libs-2.2.0-17.oe1.aarch64 |  |
| PackageKit-1.1.12-8.oe1.src | level3 | PackageKit-1.1.12-8.oe1.aarch64  |  |
| PackageKit-1.1.12-8.oe1.src | level3 | PackageKit-devel-1.1.12-8.oe1.aarch64 |  |
| adwaita-icon-theme-3.32.0-1.oe1.src | level3 |  |  |
| at-spi2-core-2.34.0-1.oe1.src | level3 | at-spi2-core-2.34.0-1.oe1.aarch64 |  |
| autoconf-2.69-30.oe1.src | level3 |  |  |
| babel-2.7.0-1.oe1.src | level3 |  |  |
| bc-1.07.1-10.oe1.src | level3 |  |  |
| cairomm-1.12.0-10.oe1.src | level3 | cairomm-1.12.0-10.oe1.aarch64 |  |
| cdparanoia-10.2-30.oe1.src | level3 | cdparanoia-libs-10.2-30.oe1.aarch64 |  |
| crypto-policies-20180925-3.git71ca85f.oe1.src | level3 |  |  |
| dmraid-1.0.0.rc16-52.oe1.src | level3 | dmraid-1.0.0.rc16-52.oe1.aarch64 |  |
| gdb-8.3.1-11.oe1.src | level3 | gdb-gdbserver-8.3.1-11.oe1.aarch64 |  |
| gdisk-1.0.4-5.oe1.src | level3 |  |  |
| geoclue2-2.4.11-4.oe1.src | level3 | geoclue2-2.4.11-4.oe1.aarch64 |  |
| geocode-glib-3.26.1-4.oe1.src | level3 | geocode-glib-3.26.1-4.oe1.aarch64 |  |
| giflib-5.1.4-6.oe1.src | level3 | giflib-5.1.4-6.oe1.aarch64 |  |
| gimp-2.10.6-7.oe1.src | level3 | gimp-2.10.6-7.oe1.aarch64  |  |
| gimp-2.10.6-7.oe1.src | level3 | gimp-libs-2.10.6-7.oe1.aarch64 |  |
| gtk+-1.2.10-90.oe1.src | level3 | gtk+-1.2.10-90.oe1.aarch64 |  |
| hostname-3.20-7.oe1.src | level3 |  |  |
| javapackages-tools-5.3.0-2.oe1.src | level3 |  |  |
| leveldb-1.20-4.oe1.src | level3 | leveldb-1.20-4.oe1.aarch64 |  |
| libXfont2-2.0.3-4.oe1.src | level3 | libXfont2-2.0.3-4.oe1.aarch64 |  |
| libev-4.24-11.oe1.src | level3 | libev-4.24-11.oe1.aarch64 |  |
| libical-3.0.4-2.oe1.src | level3 | libical-3.0.4-2.oe1.aarch64 |  |
| libieee1284-0.2.11-29.oe1.src | level3 | libieee1284-0.2.11-29.oe1.aarch64 |  |
| libindicator-12.10.1-15.oe1.src | level3 | libindicator-12.10.1-15.oe1.aarch64  |  |
| libindicator-12.10.1-15.oe1.src | level3 | libindicator-gtk3-12.10.1-15.oe1.aarch64 |  |
| libraw1394-2.1.2-8.oe1.src | level3 | libraw1394-2.1.2-8.oe1.aarch64 |  |
| libuser-0.62-20.oe1.src | level3 | libuser-0.62-20.oe1.aarch64  |  |
| libuser-0.62-20.oe1.src | level3 | libuser-python3-0.62-20.oe1.aarch64  |  |
| libuser-0.62-20.oe1.src | level3 | python2-libuser-0.62-20.oe1.aarch64 |  |
| v4l-utils-1.14.2-5.oe1.src | level3 | libv4l-1.14.2-5.oe1.aarch64  |  |
| v4l-utils-1.14.2-5.oe1.src | level3 | v4l-utils-1.14.2-5.oe1.aarch64 |  |
| lutok-0.4-13.oe1.src | level3 | lutok-0.4-13.oe1.aarch64 |  |
| m4-1.4.18-13.oe1.src | level3 |  |  |
| nautilus-3.33.90-3.oe1.src | level3 | nautilus-3.33.90-3.oe1.aarch64  |  |
| nautilus-3.33.90-3.oe1.src | level3 | nautilus-help-3.33.90-3.oe1.noarch |  |
| newt-0.52.21-3.oe1.src | level3 | newt-0.52.21-3.oe1.aarch64 |  |
| nfs-utils-2.4.2-2.oe1.src | level3 | nfs-utils-2.4.2-2.oe1.aarch64 |  |
| openEuler-logos-1.0-6.oe1.src | level3 |  |  |
| openscap-1.3.2-5.oe1.src | level3 | openscap-1.3.2-5.oe1.aarch64 |  |
| patch-2.7.6-12.oe1.src | level3 |  |  |
| perl-Digest-1.17-419.oe1.src | level3 |  |  |
| perl-ExtUtils-Install-2.14-419.oe1.src | level3 |  |  |
| perl-IO-Compress-2.081-6.oe1.src | level3 |  |  |
| plymouth-0.9.4-2.oe1.src | level3 | plymouth-0.9.4-2.oe1.aarch64 |  |
| python-cffi-1.11.5-10.oe1.src | level3 |  |  |
| python-psycopg2-2.7.5-3.2.oe1.src | level3 |  |  |
| python-pyasn1-0.3.7-8.oe1.src | level3 |  |  |
| python-pycurl-7.43.0.2-6.oe1.src | level3 |  |  |
| python-pyudev-0.21.0-10.oe1.src | level3 |  |  |
| sanlock-3.6.0-7.oe1.src | level3 | sanlock-3.6.0-7.oe1.aarch64 |  |
| setools-4.1.1-17.oe1.src | level3 |  |  |
| python-virtualenv-16.0.0-6.oe1.src | level3 |  |  |
| qt5-qtconnectivity-5.11.1-3.oe1.src | level3 | qt5-qtconnectivity-5.11.1-3.oe1.aarch64 |  |
| qt5-qtmultimedia-5.11.1-5.oe1.src | level3 | qt5-qtmultimedia-5.11.1-5.oe1.aarch64  |  |
| qt5-qtmultimedia-5.11.1-5.oe1.src | level3 | qt5-qtmultimedia-devel-5.11.1-5.oe1.aarch64 |  |
| qt5-qtsensors-5.11.1-5.oe1.src | level3 | qt5-qtsensors-5.11.1-5.oe1.aarch64 |  |
| rsync-3.1.3-6.oe1.src | level3 |  |  |
| speex-1.2.0-4.oe1.src | level3 | speex-1.2.0-4.oe1.aarch64 |  |
| tigervnc-1.10.1-2.oe1.src | level3 | tigervnc-server-module-1.10.1-2.oe1.aarch64 |  |
| tzdata-2019b-10.oe1.src | level3 |  |  |
| xkeyboard-config-2.24-6.oe1.src | level3 |  |  |
| GraphicsMagick-1.3.30-6.oe1.src | level3 | GraphicsMagick-1.3.30-6.oe1.aarch64  |  |
| GraphicsMagick-1.3.30-6.oe1.src | level3 | GraphicsMagick-c++-1.3.30-6.oe1.aarch64 |  |
| PyQt4-4.12.1-12.oe1.src | level3 |  |  |
| SDL-1.2.15-36.oe1.src | level3 | SDL-1.2.15-36.oe1.aarch64 |  |
| adobe-mappings-cmap-20190730-2.oe1.src | level3 |  |  |
| assimp-3.3.1-18.oe1.src | level3 | assimp-3.3.1-18.oe1.aarch64 |  |
| at-spi2-atk-2.30.0-2.oe1.src | level3 | at-spi2-atk-2.30.0-2.oe1.aarch64 |  |
| atkmm-2.24.2-8.oe1.src | level3 | atkmm-2.24.2-8.oe1.aarch64 |  |
| babeltrace-1.5.6-4.oe1.src | level3 | babeltrace-1.5.6-4.oe1.aarch64 |  |
| brotli-1.0.5-3.oe1.src | level3 | brotli-1.0.5-3.oe1.aarch64 |  |
| libburn-1.4.8-6.oe1.src | level3 | libburn-1.4.8-6.oe1.aarch64 |  |
| cheese-3.32.0-1.oe1.src | level3 | cheese-3.32.0-1.oe1.aarch64  |  |
| cheese-3.32.0-1.oe1.src | level3 | cheese-libs-3.32.0-1.oe1.aarch64 |  |
| clutter-gtk-1.8.4-5.oe1.src | level3 | clutter-gtk-1.8.4-5.oe1.aarch64 |  |
| createrepo_c-0.15.0-4.oe1.src | level3 | createrepo_c-0.15.0-4.oe1.aarch64 |  |
| deltarpm-3.6.2-2.oe1.src | level3 |  |  |
| dosfstools-4.1-7.oe1.src | level3 |  |  |
| dotconf-1.3-22.oe1.src | level3 | dotconf-1.3-22.oe1.aarch64 |  |
| environment-modules-4.1.4-3.oe1.src | level3 |  |  |
| freerdp-2.0.0-44.rc3.h4.oe1.src | level3 | freerdp-2.0.0-44.rc3.h4.oe1.aarch64  |  |
| freerdp-2.0.0-44.rc3.h4.oe1.src | level3 | libwinpr-2.0.0-44.rc3.h4.oe1.aarch64 |  |
| glade-3.22.1-5.oe1.src | level3 | glade-3.22.1-5.oe1.aarch64  |  |
| glade-3.22.1-5.oe1.src | level3 | glade-libs-3.22.1-5.oe1.aarch64 |  |
| glusterfs-7.0-4.oe1.src | level3 | glusterfs-7.0-4.oe1.aarch64 |  |
| groff-1.22.4-4.oe1.src | level3 |  |  |
| gssdp-1.0.2-7.oe1.src | level3 | gssdp-1.0.2-7.oe1.aarch64 |  |
| guile-2.0.14-15.oe1.src | level3 | guile-2.0.14-15.oe1.aarch64 |  |
| gvfs-1.40.2-6.oe1.src | level3 | gvfs-1.40.2-6.oe1.aarch64  |  |
| gvfs-1.40.2-6.oe1.src | level3 | gvfs-client-1.40.2-6.oe1.aarch64 |  |
| http-parser-2.9.2-2.oe1.src | level3 | http-parser-2.9.2-2.oe1.aarch64 |  |
| hunspell-1.7.0-5.oe1.src | level3 | hunspell-1.7.0-5.oe1.aarch64 |  |
| ima-evm-utils-1.2.1-8.oe1.src | level3 | ima-evm-utils-libs-1.2.1-8.oe1.aarch64 |  |
| jbigkit-2.1-17.oe1.src | level3 | jbigkit-libs-2.1-17.oe1.aarch64 |  |
| lame-3.100-7.oe1.src | level3 | lame-3.100-7.oe1.aarch64 |  |
| libIDL-0.8.14-18.oe1.src | level2 | libIDL-0.8.14-18.oe1.aarch64 |  |
| libXdmcp-1.1.3-2.oe1.src | level3 | libXdmcp-1.1.3-2.oe1.aarch64 |  |
| libao-1.2.0-14.oe1.src | level3 | libao-1.2.0-14.oe1.aarch64 |  |
| libcomps-0.1.8-20.oe1.src | level3 | libcomps-0.1.8-20.oe1.aarch64 |  |
| libconfig-1.7.2-8.oe1.src | level3 | libconfig-1.7.2-8.oe1.aarch64 |  |
| libcroco-0.6.12-13.oe1.src | level3 | libcroco-0.6.12-13.oe1.aarch64 |  |
| libdaemon-0.14-20.oe1.src | level3 | libdaemon-0.14-20.oe1.aarch64 |  |
| liberation-fonts-2.00.5-2.oe1.src | level3 |  |  |
| libestr-0.1.9-12.oe1.src | level3 | libestr-0.1.9-12.oe1.aarch64 |  |
| libevdev-1.5.9-6.oe1.src | level3 | libevdev-1.5.9-6.oe1.aarch64 |  |
| libfontenc-1.1.4-2.oe1.src | level3 | libfontenc-1.1.4-2.oe1.aarch64 |  |
| libgdata-0.17.9-4.oe1.src | level3 | libgdata-0.17.9-4.oe1.aarch64 |  |
| libgtop2-2.40.0-1.oe1.src | level3 | libgtop2-2.40.0-1.oe1.aarch64 |  |
| libgusb-0.3.0-5.oe1.src | level3 | libgusb-0.3.0-5.oe1.aarch64 |  |
| libidn-1.35-8.oe1.src | level2 | libidn-1.35-8.oe1.aarch64 |  |
| libiscsi-1.18.0-6.oe1.src | level2 |  |  |
| libnetfilter_conntrack-1.0.6-7.oe1.src | level2 | libnetfilter_conntrack-1.0.6-7.oe1.aarch64 |  |
| nghttp2-1.39.2-2.oe1.src | level3 | libnghttp2-1.39.2-2.oe1.aarch64 |  |
| libpsl-0.20.2-9.oe1.src |  | libpsl-0.20.2-9.oe1.aarch64 | Subject to modification |
| libteam-1.27-14.oe1.src | level3 | libteam-1.27-14.oe1.aarch64 |  |
| libverto-0.3.1-2.oe1.src | level3 | libverto-0.3.1-2.oe1.aarch64 |  |
| lm_sensors-3.4.0-21.oe1.src | level3 | lm_sensors-3.4.0-21.oe1.aarch64 |  |
| mailcap-2.1.48-6.oe1.src | level3 |  |  |
| marisa-0.2.4-37.oe1.src | level3 | marisa-0.2.4-37.oe1.aarch64 |  |
| passenger-6.0.4-2.oe1.src | level3 |  |  |
| mozjs60-60.2.2-4.oe1.src | level3 | mozjs60-60.2.2-4.oe1.aarch64 |  |
| nagios-4.4.3-5.oe1.src | level3 |  |  |
| ostree-2019.4-6.oe1.src | level3 | ostree-2019.4-6.oe1.aarch64 |  |
| pangomm-2.40.1-7.oe1.src | level3 | pangomm-2.40.1-7.oe1.aarch64 |  |
| pciutils-3.6.2-5.oe1.src | level3 | pciutils-3.6.2-5.oe1.aarch64 |  |
| pcsc-lite-1.8.23-9.oe1.src | level3 | pcsc-lite-1.8.23-9.oe1.aarch64  |  |
| pcsc-lite-1.8.23-9.oe1.src | level3 | pcsc-lite-devel-1.8.23-9.oe1.aarch64 |  |
| perl-CPAN-Meta-2.150010-419.oe1.src | level3 |  |  |
| perl-Compress-Raw-Zlib-2.081-6.oe1.src | level3 |  |  |
| perl-Digest-SHA-6.02-7.oe1.src | level3 |  |  |
| perl-ExtUtils-Manifest-1.71-4.oe1.src | level3 |  |  |
| perl-ExtUtils-ParseXS-3.35-1.oe1.src | level3 |  |  |
| perl-HTTP-Date-6.02-21.oe1.src | level3 |  |  |
| perl-Socket6-0.28-9.oe1.src | level3 |  |  |
| perl-Tk-804.034-5.oe1.src | level3 |  |  |
| perl-autodie-2.29-398.oe1.src | level3 |  |  |
| perl-threads-2.22-419.oe1.src | level3 |  |  |
| postfix-3.3.1-10.oe1.src | level3 |  |  |
| pyparsing-2.2.0-4.oe1.src | level3 |  |  |
| python-decorator-4.3.0-3.oe1.src | level3 |  |  |
| python-docutils-0.14-7.oe1.src | level3 |  |  |
| python-lxml-4.2.3-3.oe1.src | level3 |  |  |
| python-mako-1.0.6-12.oe1.src | level3 |  |  |
| python-pymongo-3.9.0-2.oe1.src | level3 |  |  |
| python-coverage-4.5.3-1.oe1.src | level3 |  |  |
| python-ipaddress-1.0.18-9.oe1.src | level3 |  |  |
| python-werkzeug-0.14.1-6.oe1.src | level3 |  |  |
| qt5-qtx11extras-5.11.1-5.oe1.src | level3 | qt5-qtx11extras-5.11.1-5.oe1.aarch64 |  |
| raptor2-2.0.15-17.oe1.src | level3 | raptor2-2.0.15-17.oe1.aarch64 |  |
| rpcbind-1.2.5-2.oe1.src | level3 |  |  |
| samyak-fonts-1.2.2-22.oe1.src | level3 |  |  |
| speexdsp-1.2.0-1.oe1.src | level3 | speexdsp-1.2.0-1.oe1.aarch64 |  |
| tokyocabinet-1.4.48-13.oe1.src | level3 | tokyocabinet-1.4.48-13.oe1.aarch64 |  |
| userspace-rcu-0.10.1-7.oe1.src | level3 | userspace-rcu-0.10.1-7.oe1.aarch64 |  |
| utf8proc-2.1.1-6.oe1.src | level3 |  |  |
| vala-0.42.2-2.oe1.src | level3 | vala-0.42.2-2.oe1.aarch64 |  |
| wget-1.20.3-1.oe1.src | level3 |  |  |
| xorg-x11-fonts-7.5-24.oe1.src | level3 |  |  |
| xorg-x11-xkb-utils-7.7-28.oe1.src | level3 |  |  |
| OpenIPMI-2.0.27-5.oe1.src | level3 | OpenIPMI-2.0.27-5.oe1.aarch64  |  |
| OpenIPMI-2.0.27-5.oe1.src | level3 | OpenIPMI-perl-2.0.27-5.oe1.aarch64 |  |
| abattis-cantarell-fonts-0.111-2.oe1.src | level3 |  |  |
| authselect-1.0.1-5.oe1.src | level3 | authselect-1.0.1-5.oe1.aarch64 |  |
| bacula-9.4.4-2.oe1.src | level3 | bacula-common-9.4.4-2.oe1.aarch64 |  |
| bison-3.5-2.oe1.src | level3 |  |  |
| btrfs-progs-4.17.1-3.oe1.src | level3 | btrfs-progs-4.17.1-3.oe1.aarch64  |  |
| btrfs-progs-4.17.1-3.oe1.src | level3 | btrfs-progs-devel-4.17.1-3.oe1.aarch64 |  |
| build-20191114-324.3.oe1.src | level3 |  |  |
| clucene-2.3.3.4-35.oe1.src | level3 | clucene-contribs-lib-2.3.3.4-35.oe1.aarch64  |  |
| clucene-2.3.3.4-35.oe1.src | level3 | clucene-core-2.3.3.4-35.oe1.aarch64 |  |
| clutter-gst3-3.0.26-4.oe1.src | level3 | clutter-gst3-3.0.26-4.oe1.aarch64 |  |
| cronie-1.5.4-5.oe1.src | level3 |  |  |
| custom_build_tool-1.0-17.oe1.oe1.src | level3 |  |  |
| dnf-plugins-core-4.0.11-5.oe1.src | level3 |  |  |
| docbook-style-xsl-1.79.2-9.oe1.src | level3 |  |  |
| drpm-0.4.1-3.oe1.src | level3 | drpm-0.4.1-3.oe1.aarch64 |  |
| efi-rpm-macros-4-2.oe1.src | level3 |  |  |
| exiv2-0.26-17.oe1.src | level3 | exiv2-0.26-17.oe1.aarch64 |  |
| fribidi-1.0.5-4.oe1.src | level3 | fribidi-1.0.5-4.oe1.aarch64 |  |
| gamin-0.1.10-37.oe1.src | level3 | gamin-0.1.10-37.oe1.aarch64 |  |
| gtest-1.8.1-3.oe1.src | level3 | gmock-1.8.1-3.oe1.aarch64  |  |
| gtest-1.8.1-3.oe1.src | level3 | gtest-1.8.1-3.oe1.aarch64 |  |
| gnome-control-center-3.30.1-10.oe1.src | level3 | gnome-control-center-3.30.1-10.oe1.aarch64 |  |
| gnome-shell-3.30.1-5.oe1.src | level3 | gnome-shell-3.30.1-5.oe1.aarch64 |  |
| golang-1.13-3.3.oe1.src | level3 | golang-help-1.13-3.3.oe1.noarch |  |
| graphite2-1.3.13-2.oe1.src | level3 | graphite2-1.3.13-2.oe1.aarch64 |  |
| gsm-1.0.18-4.oe1.src | level3 | gsm-1.0.18-4.oe1.aarch64 |  |
| gstreamer-0.10.36-19.oe1.src | level3 | gstreamer-0.10.36-19.oe1.aarch64  |  |
| gstreamer-0.10.36-19.oe1.src | level3 | gstreamer-devel-0.10.36-19.oe1.aarch64 |  |
| gupnp-1.0.3-2.oe1.src | level3 | gupnp-1.0.3-2.oe1.aarch64 |  |
| hesiod-3.2.1-16.oe1.src | level3 | hesiod-3.2.1-16.oe1.aarch64 |  |
| kae_driver-1.2.10-4.oe1.src | level3 |  |  |
| hwloc-1.11.9-3.oe1.src | level3 | hwloc-1.11.9-3.oe1.aarch64 |  |
| hyphen-2.8.8-13.oe1.src | level3 | hyphen-2.8.8-13.oe1.aarch64 |  |
| i2c-tools-4.1-1.oe1.src | level3 | i2c-tools-4.1-1.oe1.aarch64 |  |
| iputils-20190709-2.oe1.src | level3 |  |  |
| irrlicht-1.8.4-11.oe1.src | level3 | irrlicht-1.8.4-11.oe1.aarch64 |  |
| kbd-2.0.4-11.oe1.src | level3 |  |  |
| kexec-tools-2.0.17-15.oe1.src | level3 |  |  |
| less-551-3.oe1.src | level3 |  |  |
| libXp-1.0.3-6.oe1.src | level3 | libXp-1.0.3-6.oe1.aarch64 |  |
| libXres-1.2.0-7.oe1.src | level3 | libXres-1.2.0-7.oe1.aarch64 |  |
| libappindicator-12.10.0-25.oe1.src | level3 | libappindicator-12.10.0-25.oe1.aarch64  |  |
| libappindicator-12.10.0-25.oe1.src | level3 | libappindicator-gtk3-12.10.0-25.oe1.aarch64 |  |
| libbonoboui-2.24.5-17.oe1.src | level3 | libbonoboui-2.24.5-17.oe1.aarch64 |  |
| libbytesize-1.4-8.oe1.src | level3 | libbytesize-1.4-8.oe1.aarch64 |  |
| libcdio-2.0.0-8.oe1.src | level3 | libcdio-2.0.0-8.oe1.aarch64 |  |
| libdbi-0.9.0-15.oe1.src | level3 | libdbi-0.9.0-15.oe1.aarch64 |  |
| libdvdread-6.0.0-3.oe1.src | level3 | libdvdread-6.0.0-3.oe1.aarch64 |  |
| libgee-0.20.1-5.oe1.src | level3 | libgee-0.20.1-5.oe1.aarch64 |  |
| libgit2-0.27.8-3.oe1.src | level3 | libgit2-0.27.8-3.oe1.aarch64 |  |
| libgnomekbd-3.26.1-1.oe1.src | level3 | libgnomekbd-3.26.1-1.oe1.aarch64 |  |
| libimagequant-2.12.5-2.oe1.src | level3 | libimagequant-2.12.5-2.oe1.aarch64 |  |
| libimobiledevice-1.2.0-18.oe1.src | level3 | libimobiledevice-1.2.0-18.oe1.aarch64 |  |
| libisoburn-1.4.8-6.oe1.src | level3 | libisoburn-1.4.8-6.oe1.aarch64 |  |
| libisofs-1.4.8-5.oe1.src | level3 | libisofs-1.4.8-5.oe1.aarch64 |  |
| liblognorm-2.0.3-7.oe1.src | level3 | liblognorm-2.0.3-7.oe1.aarch64 |  |
| libmbim-1.16.0-5.oe1.src | level3 | libmbim-1.16.0-5.oe1.aarch64 |  |
| libmetalink-0.1.3-8.oe1.src | level3 | libmetalink-0.1.3-8.oe1.aarch64 |  |
| libmicrohttpd-0.9.59-4.oe1.src | level3 | libmicrohttpd-0.9.59-4.oe1.aarch64 |  |
| libmikmod-3.3.11.1-5.oe1.src | level3 | libmikmod-3.3.11.1-5.oe1.aarch64 |  |
| sendmail-8.15.2-33.oe1.src | level3 | libmilter-8.15.2-33.oe1.aarch64 |  |
| libmng-2.0.3-10.oe1.src | level3 | libmng-2.0.3-10.oe1.aarch64 |  |
| liboauth-1.0.3-13.oe1.src | level2 | liboauth-1.0.3-13.oe1.aarch64 |  |
| libpaper-1.1.24-25.oe1.src | level3 | libpaper-1.1.24-25.oe1.aarch64 |  |
| libserf-1.3.9-12.oe1.src | level2 |  |  |
| libunwind-1.3.1-3.oe1.src | level2 | libunwind-1.3.1-3.oe1.aarch64 |  |
| libutempter-1.1.6-16.oe1.src | level3 | libutempter-1.1.6-16.oe1.aarch64 |  |
| libuv-1.23.0-2.oe1.src | level3 | libuv-1.23.0-2.oe1.aarch64 |  |
| libvoikko-4.1.1-3.oe1.src | level3 | libvoikko-4.1.1-3.oe1.aarch64 |  |
| libxklavier-5.4-18.oe1.src | level3 | libxklavier-5.4-18.oe1.aarch64 |  |
| lockdev-1.0.4-0.31.oe1.src | level3 | lockdev-1.0.4-0.31.oe1.aarch64 |  |
| lsof-4.93.2-3.oe1.src | level3 |  |  |
| lttng-ust-2.10.1-6.oe1.src | level3 | lttng-ust-2.10.1-6.oe1.aarch64 |  |
| lxc-3.0.3-2020031002.oe1.src | level3 | lxc-libs-3.0.3-2020031002.oe1.aarch64 |  |
| m17n-db-1.8.0-5.oe1.src | level3 |  |  |
| m17n-lib-1.8.0-5.oe1.src | level3 | m17n-lib-1.8.0-5.oe1.aarch64  |  |
| m17n-lib-1.8.0-5.oe1.src | level3 | m17n-lib-tools-1.8.0-5.oe1.aarch64 |  |
| mongodb-4.0.1-3.oe1.src | level3 |  |  |
| netpbm-10.83.01-3.oe1.src | level3 | netpbm-10.83.01-3.oe1.aarch64 |  |
| nmap-7.70-11.oe1.src | level3 |  |  |
| openjade-1.3.2-60.oe1.src | level3 | openjade-1.3.2-60.oe1.aarch64 |  |
| openslp-2.0.0-24.oe1.src | level3 | openslp-2.0.0-24.oe1.aarch64 |  |
| opensp-1.5.2-31.oe1.src | level3 | opensp-1.5.2-31.oe1.aarch64 |  |
| overpass-fonts-3.0.3-4.oe1.src | level3 |  |  |
| perl-Archive-Tar-2.30-421.oe1.src | level3 |  |  |
| perl-Filter-1.59-2.oe1.src | level3 |  |  |
| perl-HTTP-Tiny-0.076-3.oe1.src | level3 |  |  |
| perl-IO-Socket-IP-0.39-420.oe1.src | level3 |  |  |
| perl-IO-Socket-SSL-2.066-4.oe1.src | level3 |  |  |
| perl-Module-Metadata-1.000036-3.oe1.src | level3 |  |  |
| perl-Params-Check-0.38-418.oe1.src | level3 |  |  |
| perl-Perl-OSType-1.010-421.oe1.src | level3 |  |  |
| perl-Test-Harness-3.43_01-3.oe1.src | level3 |  |  |
| perl-Unicode-Normalize-1.26-419.oe1.src | level3 |  |  |
| perl-threads-shared-1.59-2.oe1.src | level3 |  |  |
| phonon-4.10.1-4.oe1.src | level3 | phonon-4.10.1-4.oe1.aarch64  |  |
| phonon-4.10.1-4.oe1.src | level3 | phonon-qt5-4.10.1-4.oe1.aarch64 |  |
| proj-4.9.3-8.oe1.src | level3 | proj-4.9.3-8.oe1.aarch64 |  |
| pykickstart-3.16-2.oe1.src | level3 |  |  |
| python-attrs-17.4.0-9.oe1.src | level3 |  |  |
| python-enum34-1.1.6-8.oe1.src | level3 |  |  |
| python-funcsigs-1.0.2-13.oe1.src | level3 |  |  |
| python-iniparse-0.4-36.oe1.src | level3 |  |  |
| python-markupsafe-1.0-3.oe1.src | level3 |  |  |
| python-pip-18.0-12.oe1.src | level3 |  |  |
| python-ply-3.9-9.oe1.src | level3 |  |  |
| python-pygments-2.2.0-15.oe1.src | level3 |  |  |
| qpid-proton-0.24.0-5.oe1.src | level3 | qpid-proton-c-cpp-0.24.0-5.oe1.aarch64 |  |
| python-rtslib-2.1.70-3.oe1.src | level3 |  |  |
| python-contextlib2-0.5.5-9.oe1.src | level3 |  |  |
| python-execnet-1.5.0-5.oe1.src | level3 |  |  |
| python-extras-1.0.0-6.oe1.src | level3 |  |  |
| python-greenlet-0.4.14-2.oe1.src | level3 |  |  |
| python-path-5.2-15.oe1.src | level3 |  |  |
| python-slip-0.6.5-4.oe1.src | level3 |  |  |
| tbb-2018.5-4.oe1.src |  | tbb-2018.5-4.oe1.aarch64 | Subject to modification |
| python-traceback2-1.4.0-19.oe1.src | level3 |  |  |
| python-webob-1.8.2-3.oe1.src | level3 |  |  |
| qt5-qtscript-5.11.1-4.oe1.src | level3 | qt5-qtscript-5.11.1-4.oe1.aarch64 |  |
| qt5-qtwayland-5.11.1-4.oe1.src | level3 | qt5-qtwayland-5.11.1-4.oe1.aarch64 |  |
| qt5-qtwebengine-5.11.1-7.oe1.src | level3 | qt5-qtwebengine-5.11.1-7.oe1.aarch64 |  |
| redland-1.0.17-17.oe1.src | level3 | redland-1.0.17-17.oe1.aarch64  |  |
| redland-1.0.17-17.oe1.src | level3 | redland-mysql-1.0.17-17.oe1.aarch64  |  |
| redland-1.0.17-17.oe1.src | level3 | redland-pgsql-1.0.17-17.oe1.aarch64 |  |
| spice-gtk-0.35-4.oe1.src | level3 | spice-gtk-0.35-4.oe1.aarch64 |  |
| squashfs-tools-4.4-1.oe1.src | level3 |  |  |
| sysfsutils-2.1.0-28.oe1.src | level3 | sysfsutils-2.1.0-28.oe1.aarch64 |  |
| totem-pl-parser-3.26.1-5.oe1.src | level3 | totem-pl-parser-3.26.1-5.oe1.aarch64 |  |
| tpm2-tss-2.0.1-4.oe1.src | level3 | tpm2-tss-2.0.1-4.oe1.aarch64 |  |
| tracker-2.1.5-3.oe1.src | level3 | tracker-2.1.5-3.oe1.aarch64 |  |
| trousers-0.3.14-3.oe1.src | level3 | trousers-0.3.14-3.oe1.aarch64 |  |
| vte291-0.54.1-4.oe1.src | level3 | vte291-0.54.1-4.oe1.aarch64 |  |
| xcb-util-0.4.0-13.oe1.src | level3 | xcb-util-0.4.0-13.oe1.aarch64 |  |
| Xaw3d-1.6.3-1.oe1.src | level3 | Xaw3d-1.6.3-1.oe1.aarch64 |  |
| aspell-0.60.6.1-25.oe1.src | level3 | aspell-0.60.6.1-25.oe1.aarch64 |  |
| autogen-5.18.14-4.oe1.src | level3 | autogen-5.18.14-4.oe1.aarch64 |  |
| python-blivet-3.1.1-8.oe1.src | level3 |  |  |
| bubblewrap-0.3.3-1.oe1.src | level3 |  |  |
| capstone-4.0.1-2.oe1.src | level3 | capstone-4.0.1-2.oe1.aarch64  |  |
| capstone-4.0.1-2.oe1.src | level3 | python2-capstone-4.0.1-2.oe1.aarch64  |  |
| capstone-4.0.1-2.oe1.src | level3 | python3-capstone-4.0.1-2.oe1.aarch64 |  |
| check-0.12.0-4.oe1.src | level3 | check-0.12.0-4.oe1.aarch64 |  |
| checkpolicy-2.8-6.oe1.src | level3 |  |  |
| phodav-2.2-6.oe1.src | level3 | libphodav-2.2-6.oe1.aarch64 |  |
| clevis-11-3.oe1.src | level3 |  |  |
| cockpit-178-7.oe1.src | level3 | cockpit-178-7.oe1.aarch64 |  |
| colord-gtk-0.1.26-10.oe1.src | level3 | colord-gtk-0.1.26-10.oe1.aarch64 |  |
| cppunit-1.14.0-7.oe1.src | level3 | cppunit-1.14.0-7.oe1.aarch64 |  |
| crash-7.2.6-3.oe1.src | level3 |  |  |
| dhcp-4.3.6-37.oe1.src | level3 | dhcp-4.3.6-37.oe1.aarch64 |  |
| dpkg-1.18.25-10.oe1.src | level3 |  |  |
| dtc-1.4.7-3.oe1.src | level3 | dtc-1.4.7-3.oe1.aarch64 |  |
| ethtool-5.3-2.oe1.src | level3 |  |  |
| libewf-20140608-13.oe1.src | level3 | libewf-20140608-13.oe1.aarch64 |  |
| exempi-2.4.5-4.oe1.src | level3 | exempi-2.4.5-4.oe1.aarch64 |  |
| expect-5.45.4-3.oe1.src | level3 | expect-5.45.4-3.oe1.aarch64 |  |
| fipscheck-1.5.0-7.oe1.src | level3 | fipscheck-1.5.0-7.oe1.aarch64 |  |
| firewalld-0.6.2-4.oe1.src | level3 | firewalld-0.6.2-4.oe1.noarch |  |
| fltk-1.3.5-1.oe1.src | level3 | fltk-1.3.5-1.oe1.aarch64 |  |
| fontforge-20170731-11.oe1.src | level3 | fontforge-20170731-11.oe1.aarch64 |  |
| fonttools-3.29.0-2.oe1.src | level3 |  |  |
| freeglut-3.0.0-10.oe1.src | level3 | freeglut-3.0.0-10.oe1.aarch64 |  |
| gc-8.0.4-2.oe1.src | level3 | gc-8.0.4-2.oe1.aarch64 |  |
| gdm-3.30.1-7.oe1.src | level3 | gdm-3.30.1-7.oe1.aarch64 |  |
| gjs-1.54.1-5.oe1.src | level3 | gjs-1.54.1-5.oe1.aarch64 |  |
| glib-networking-2.58.0-7.oe1.src |  | glib-networking-2.58.0-7.oe1.aarch64 | Subject to modification |
| gmime30-3.2.0-4.oe1.src | level3 | gmime30-3.2.0-4.oe1.aarch64 |  |
| gnome-settings-daemon-3.30.1.2-2.oe1.src | level3 | gnome-settings-daemon-3.30.1.2-2.oe1.aarch64 |  |
| grpc-1.22.0-3.oe1.src | level3 | grpc-1.22.0-3.oe1.aarch64 |  |
| gstreamer1-plugins-bad-free-1.14.4-6.oe1.src | level3 | gstreamer1-plugins-bad-free-1.14.4-6.oe1.aarch64 |  |
| infiniband-diags-2.1.0-2.oe1.src | level3 | infiniband-diags-2.1.0-2.oe1.aarch64 |  |
| ipcalc-0.2.5-1.oe1.src | level3 |  |  |
| ipset-7.3-2.oe1.src | level3 | ipset-libs-7.3-2.oe1.aarch64 |  |
| isomd5sum-1.2.3-4.oe1.src | level3 |  |  |
| jose-10-5.oe1.src | level3 | jose-10-5.oe1.aarch64 |  |
| jsoncpp-1.8.4-6.oe1.src | level3 | jsoncpp-1.8.4-6.oe1.aarch64 |  |
| kde-filesystem-4-62.oe1.src | level3 |  |  |
| kiwi-9.19.15-2.oe1.src | level3 |  |  |
| libXScrnSaver-1.2.3-4.oe1.src | level3 | libXScrnSaver-1.2.3-4.oe1.aarch64 |  |
| libXxf86misc-1.0.4-4.oe1.src | level3 | libXxf86misc-1.0.4-4.oe1.aarch64 |  |
| libaec-1.0.4-1.oe1.src | level3 | libaec-1.0.4-1.oe1.aarch64 |  |
| libappstream-glib-0.7.14-3.oe1.src | level3 | libappstream-glib-0.7.14-3.oe1.aarch64 |  |
| libasyncns-0.8-17.oe1.src | level3 | libasyncns-0.8-17.oe1.aarch64 |  |
| libcacard-2.7.0-3.oe1.src | level3 | libcacard-2.7.0-3.oe1.aarch64 |  |
| libcgroup-0.41-23.oe1.src | level2 | libcgroup-0.41-23.oe1.aarch64 |  |
| libdatrie-0.2.12-1.oe1.src | level3 | libdatrie-0.2.12-1.oe1.aarch64 |  |
| libdmx-1.1.4-6.oe1.src | level3 | libdmx-1.1.4-6.oe1.aarch64 |  |
| libdnet-1.12-28.oe1.src | level3 | libdnet-1.12-28.oe1.aarch64 |  |
| libdv-1.0.0-30.oe1.src | level3 | libdv-1.0.0-30.oe1.aarch64 |  |
| libdwarf-20180809-2.oe1.src | level3 | libdwarf-20180809-2.oe1.aarch64 |  |
| libgit2-glib-0.27.8-2.oe1.src | level3 | libgit2-glib-0.27.8-2.oe1.aarch64 |  |
| libgnome-keyring-3.12.0-16.oe1.src | level3 | libgnome-keyring-3.12.0-16.oe1.aarch64 |  |
| libgphoto2-2.5.18-3.oe1.src | level3 | libgphoto2-2.5.18-3.oe1.aarch64 |  |
| libgsf-1.14.43-4.oe1.src | level3 | libgsf-1.14.43-4.oe1.aarch64 |  |
| libgxps-0.3.1-1.oe1.src | level3 | libgxps-0.3.1-1.oe1.aarch64 |  |
| libibmad-1.3.13-7.oe1.src | level3 | libibmad-1.3.13-7.oe1.aarch64 |  |
| libiptcdata-1.0.5-1.oe1.src | level3 | libiptcdata-1.0.5-1.oe1.aarch64 |  |
| libmad-0.15.1b-28.oe1.src | level3 | libmad-0.15.1b-28.oe1.aarch64 |  |
| libnet-1.1.6-17.oe1.src | level2 | libnet-1.1.6-17.oe1.aarch64 |  |
| libnftnl-1.1.1-6.oe1.src | level3 | libnftnl-1.1.1-6.oe1.aarch64 |  |
| libnice-0.1.14-10.oe1.src | level3 | libnice-0.1.14-10.oe1.aarch64  |  |
| libnice-0.1.14-10.oe1.src | level3 | libnice-gstreamer1-0.1.14-10.oe1.aarch64 |  |
| libomxil-bellagio-0.9.3-21.oe1.src | level3 | libomxil-bellagio-0.9.3-21.oe1.aarch64 |  |
| libotf-0.9.13-13.oe1.src | level3 | libotf-0.9.13-13.oe1.aarch64 |  |
| libpinyin-2.2.0-3.oe1.src | level3 | libpinyin-2.2.0-3.oe1.aarch64 |  |
| libpipeline-1.5.0-6.oe1.src | level3 | libpipeline-1.5.0-6.oe1.aarch64 |  |
| libqmi-1.20.0-5.oe1.src | level3 | libqmi-1.20.0-5.oe1.aarch64 |  |
| librabbitmq-0.9.0-3.oe1.src | level3 | librabbitmq-0.9.0-3.oe1.aarch64 |  |
| libsamplerate-0.1.9-4.oe1.src | level3 | libsamplerate-0.1.9-4.oe1.aarch64 |  |
| libsodium-1.0.16-7.oe1.src | level3 | libsodium-1.0.16-7.oe1.aarch64 |  |
| libtar-1.2.20-17.oe1.src | level2 | libtar-1.2.20-17.oe1.aarch64 |  |
| tidy-5.6.0-1.oe1.src | level3 | libtidy-5.6.0-1.oe1.aarch64 |  |
| libtomcrypt-1.18.2-3.oe1.src | level2 | libtomcrypt-1.18.2-3.oe1.aarch64 |  |
| libtommath-1.0.1-5.oe1.src | level3 | libtommath-1.0.1-5.oe1.aarch64 |  |
| libusbmuxd-1.0.10-12.oe1.src | level2 | libusbmuxd-1.0.10-12.oe1.aarch64 |  |
| libvisual-0.4.0-27.oe1.src | level3 | libvisual-0.4.0-27.oe1.aarch64 |  |
| libvpx-1.7.0-8.oe1.src | level3 | libvpx-1.7.0-8.oe1.aarch64 |  |
| lmdb-0.9.22-4.oe1.src | level3 | lmdb-0.9.22-4.oe1.aarch64 |  |
| luksmeta-9-3.oe1.src | level3 | luksmeta-9-3.oe1.aarch64 |  |
| mailx-12.5-32.oe1.src | level3 |  |  |
| mdadm-4.1-rc2.0.9.oe1.src | level3 |  |  |
| mesa-demos-8.3.0-13.oe1.src | level3 |  |  |
| motif-2.3.4-18.oe1.src | level3 | motif-2.3.4-18.oe1.aarch64 |  |
| mozilla-filesystem-1.9-21.oe1.src | level3 |  |  |
| mpg123-1.25.10-2.oe1.src | level3 | mpg123-1.25.10-2.oe1.aarch64 |  |
| mpich-3.2.1-10.oe1.src | level3 | mpich-3.2.1-10.oe1.aarch64 |  |
| mtdev-1.1.5-15.oe1.src | level3 | mtdev-1.1.5-15.oe1.aarch64 |  |
| net-tools-2.0-0.54.oe1.src | level3 |  |  |
| netcf-0.2.8-17.oe1.src | level3 |  |  |
| nftables-0.9.0-3.oe1.src | level3 | nftables-0.9.0-3.oe1.aarch64 |  |
| obs-bundled-gems-2.10.1-0.oe1.src | level3 | obs-bundled-gems-2.10.1-0.oe1.aarch64 |  |
| opencc-1.0.5-4.oe1.src | level3 | opencc-1.0.5-4.oe1.aarch64 |  |
| openjpeg-1.5.1-24.oe1.src | level3 | openjpeg-libs-1.5.1-24.oe1.aarch64 |  |
| perl-CPAN-Meta-Requirements-2.140-419.oe1.src | level3 |  |  |
| perl-Devel-PPPort-3.42-4.oe1.src | level3 |  |  |
| perl-Digest-HMAC-1.03-20.oe1.src | level3 |  |  |
| perl-Digest-SHA1-2.13-26.oe1.src | level3 |  |  |
| perl-Encode-Locale-1.05-12.oe1.src | level3 |  |  |
| perl-File-Which-1.22-6.oe1.src | level3 |  |  |
| perl-HTML-Parser-3.72-16.oe1.src | level3 |  |  |
| perl-IO-All-0.87-7.oe1.src | level3 |  |  |
| perl-IO-Socket-INET6-2.72-15.oe1.src | level3 |  |  |
| perl-IPC-Cmd-1.04-4.oe1.src | level3 |  |  |
| perl-JSON-2.97.001-6.oe1.src | level3 |  |  |
| perl-JSON-PP-4.04-2.oe1.src | level3 |  |  |
| perl-LWP-MediaTypes-6.02-17.oe1.src | level3 |  |  |
| perl-Module-CoreList-5.20180920-2.oe1.src | level3 |  |  |
| perl-Module-Load-0.32-418.oe1.src | level3 |  |  |
| perl-Module-Load-Conditional-0.68-418.oe1.src | level3 |  |  |
| perl-Net-HTTP-6.18-4.oe1.src | level3 |  |  |
| perl-Net-SSLeay-1.88-5.oe1.src | level3 |  |  |
| perl-Pod-Markdown-3.101-2.oe1.src | level3 |  |  |
| perl-Pod-Parser-1.63-397.oe1.src | level3 |  |  |
| perl-Sys-Syslog-0.35-420.oe1.src | level3 |  |  |
| perl-TermReadKey-2.38-2.oe1.src | level3 |  |  |
| perl-Text-Balanced-2.03-420.oe1.src | level3 |  |  |
| perl-Text-Unidecode-1.30-8.oe1.src | level3 |  |  |
| perl-XML-NamespaceSupport-1.12-7.oe1.src | level3 |  |  |
| perl-XML-SAX-1.00-5.oe1.src | level3 |  |  |
| perl-libintl-perl-1.29-6.oe1.src | level3 |  |  |
| pipewire-0.2.7-1.oe1.src | level3 | pipewire-0.2.7-1.oe1.aarch64 |  |
| pygobject2-2.28.7-5.oe1.src | level3 | pygobject2-2.28.7-5.oe1.aarch64 |  |
| python-backports-ssl_match_hostname-3.7.0.1-2.oe1.src | level3 |  |  |
| python-enchant-2.0.0-6.oe1.src | level3 |  |  |
| python-jsonpointer-1.10-15.oe1.src | level3 |  |  |
| python-kmod-0.9-21.oe1.src | level3 |  |  |
| python-prettytable-0.7.2-18.oe1.src | level3 |  |  |
| python-pycparser-2.19-1.oe1.src | level3 |  |  |
| python-pysocks-1.7.0-2.oe1.src | level3 |  |  |
| python-chardet-3.0.4-8.oe1.src | level3 |  |  |
| python-crypto-2.6.1-25.oe1.src | level3 |  |  |
| python-docker-pycreds-0.4.0-1.oe1.src | level3 |  |  |
| python-flask-1.0.4-3.oe1.src | level3 |  |  |
| python-humanize-0.5.1-16.oe1.src | level3 |  |  |
| python-ordered-set-2.0.2-1.oe1.src | level3 |  |  |
| pyatspi-2.33.92-1.oe1.src | level3 |  |  |
| python-pytoml-0.1.18-2.oe1.src | level3 |  |  |
| scipy-1.2.2-2.oe1.src | level3 |  |  |
| python-singledispatch-3.4.0.3-14.oe1.src | level3 |  |  |
| subunit-1.3.0-3.oe1.src | level3 | subunit-1.3.0-3.oe1.aarch64 |  |
| python-systemd-234-10.oe1.src | level3 |  |  |
| python-urllib3-1.24.3-2.oe1.src | level3 |  |  |
| python-websocket-client-0.47.0-6.oe1.src | level3 |  |  |
| volume_key-0.3.12-2.oe1.src | level3 | volume_key-0.3.12-2.oe1.aarch64 |  |
| qt-assistant-adp-4.6.3-20.oe1.src | level3 | qt-assistant-adp-devel-4.6.3-20.oe1.aarch64 |  |
| qt5-5.11.1-7.oe1.src | level3 |  |  |
| qt5-qtenginio-1.6.2-19.oe1.src | level3 | qt5-qtenginio-1.6.2-19.oe1.aarch64 |  |
| qt5-qtquickcontrols2-5.11.1-3.oe1.src | level3 | qt5-qtquickcontrols2-5.11.1-3.oe1.aarch64 |  |
| qt5-qtwebkit-5.212.0-2.oe1.src | level3 | qt5-qtwebkit-5.212.0-2.oe1.aarch64 |  |
| qtwebkit-2.3.4-29.oe1.src | level3 | qtwebkit-2.3.4-29.oe1.aarch64 |  |
| rarian-0.8.1-23.oe1.src | level3 | rarian-0.8.1-23.oe1.aarch64 |  |
| rasqal-0.9.33-9.oe1.src | level3 | rasqal-0.9.33-9.oe1.aarch64 |  |
| rhash-1.3.5-5.oe1.src | level3 | rhash-1.3.5-5.oe1.aarch64 |  |
| sbc-1.4-1.oe1.src | level3 | sbc-1.4-1.oe1.aarch64 |  |
| sblim-sfcc-2.2.8-11.oe1.src | level3 | sblim-sfcc-2.2.8-11.oe1.aarch64 |  |
| setroubleshoot-3.3.20-2.oe1.src | level3 | setroubleshoot-3.3.20-2.oe1.aarch64 |  |
| source-highlight-3.1.8-23.oe1.src | level3 | source-highlight-3.1.8-23.oe1.aarch64 |  |
| suitesparse-4.4.6-17.oe1.src | level3 | suitesparse-4.4.6-17.oe1.aarch64 |  |
| taglib-1.11.1-12.oe1.src | level3 | taglib-1.11.1-12.oe1.aarch64 |  |
| telepathy-filesystem-0.0.2-7.oe1.src | level3 |  |  |
| twolame-0.3.13-13.oe1.src | level3 | twolame-0.3.13-13.oe1.aarch64 |  |
| uboot-tools-2018.09-8.oe1.src | level3 |  |  |
| unique-1.1.6-22.oe1.src | level3 | unique-1.1.6-22.oe1.aarch64 |  |
| usbredir-0.8.0-4.oe1.src | level3 | usbredir-0.8.0-4.oe1.aarch64 |  |
| ustr-1.0.4-28.oe1.src | level3 | ustr-1.0.4-28.oe1.aarch64  |  |
| ustr-1.0.4-28.oe1.src | level3 | ustr-debug-1.0.4-28.oe1.aarch64 |  |
| vulkan-loader-1.1.92.0-2.oe1.src |  | vulkan-loader-1.1.92.0-2.oe1.aarch64 | Subject to modification |
| wavpack-5.1.0-10.oe1.src | level3 | wavpack-5.1.0-10.oe1.aarch64 |  |
| webrtc-audio-processing-0.3.1-3.oe1.src | level3 | webrtc-audio-processing-0.3.1-3.oe1.aarch64 |  |
| wireshark-2.6.2-6.oe1.src | level3 | wireshark-2.6.2-6.oe1.aarch64 |  |
| woff2-1.0.2-6.oe1.src | level3 | woff2-1.0.2-6.oe1.aarch64 |  |
| xcb-util-image-0.4.0-12.oe1.src | level3 | xcb-util-image-0.4.0-12.oe1.aarch64 |  |
| xcb-util-keysyms-0.4.0-10.oe1.src | level3 | xcb-util-keysyms-0.4.0-10.oe1.aarch64 |  |
| xcb-util-renderutil-0.3.9-13.oe1.src | level3 | xcb-util-renderutil-0.3.9-13.oe1.aarch64 |  |
| xcb-util-wm-0.4.1-15.oe1.src | level3 | xcb-util-wm-0.4.1-15.oe1.aarch64 |  |
| xmlrpc-c-1.51.03-4.oe1.src | level3 | xmlrpc-c-1.51.03-4.oe1.aarch64 |  |
| xorg-x11-xauth-1.1-1.oe1.src | level3 |  |  |
| yelp-xsl-3.34.0-1.oe1.src | level3 |  |  |
| zip-3.0-25.oe1.src | level3 |  |  |
| zziplib-0.13.69-5.oe1.src | level3 | zziplib-0.13.69-5.oe1.aarch64 |  |
| CUnit-2.1.3-21.oe1.src | level3 |  |  |
| Judy-1.0.5-19.oe1.src | level3 | Judy-1.0.5-19.oe1.aarch64 |  |
| LibRaw-0.19.0-9.oe1.src | level3 | LibRaw-0.19.0-9.oe1.aarch64 |  |
| adobe-mappings-pdf-20190401-1.oe1.src | level3 |  |  |
| afflib-3.7.16-9.oe1.src | level3 | afflib-3.7.16-9.oe1.aarch64 |  |
| alsa-tools-1.1.6-4.oe1.src | level3 | alsa-tools-1.1.6-4.oe1.aarch64 |  |
| anthy-9100h-39.oe1.src | level3 | anthy-9100h-39.oe1.aarch64 |  |
| atune-0.2-0.1.oe1.src | level3 |  |  |
| bash-completion-2.8-9.oe1.src | level3 |  |  |
| bolt-0.5-2.oe1.src | level3 |  |  |
| boom-boot-0.9-5.oe1.src | level3 |  |  |
| breeze-icon-theme-5.50.0-2.oe1.src | level3 |  |  |
| chrony-3.4-3.oe1.src | level3 |  |  |
| cjkuni-ukai-fonts-0.2.20080216.1-61.oe1.src | level3 |  |  |
| cldr-emoji-annotation-33.1.0_0-3.oe1.src | level3 |  |  |
| clibcni-2.0.0-20200318.003112.git275f9ee3.oe1.src | level3 | clibcni-2.0.0-20200318.003112.git275f9ee3.oe1.aarch64 |  |
| lorax-29.16-10.oe1.src | level3 |  |  |
| skopeo-0.1.32-2.dev.gite814f96.oe1.src | level3 |  |  |
| cups-filters-1.26.1-2.oe1.src | level3 | cups-filters-1.26.1-2.oe1.aarch64 |  |
| dialog-1.3-17.oe1.src | level3 | dialog-1.3-17.oe1.aarch64 |  |
| dmidecode-3.2-2.oe1.src | level3 |  |  |
| docbook-utils-0.6.14-47.oe1.src | level3 |  |  |
| doxygen-1.8.14-10.oe1.src | level3 |  |  |
| ebtables-2.0.10-32.oe1.src | level3 | ebtables-2.0.10-32.oe1.aarch64 |  |
| espeak-ng-1.49.2-5.oe1.src | level3 | espeak-ng-1.49.2-5.oe1.aarch64 |  |
| festival-1.96-40.oe1.src | level3 | festival-1.96-40.oe1.aarch64 |  |
| firebird-3.0.3.32900-6.oe1.src | level3 | firebird-3.0.3.32900-6.oe1.aarch64 |  |
| flite-1.3-33.oe1.src | level3 | flite-1.3-33.oe1.aarch64 |  |
| folks-0.11.4-10.oe1.src | level3 | folks-0.11.4-10.oe1.aarch64 |  |
| fontawesome-fonts-4.7.0-7.oe1.src | level3 |  |  |
| fprintd-0.8.1-4.oe1.src | level3 |  |  |
| freeipmi-1.6.2-4.oe1.src | level3 | freeipmi-1.6.2-4.oe1.aarch64 |  |
| freetds-1.00.38-7.oe1.src | level3 | freetds-1.00.38-7.oe1.aarch64 |  |
| frei0r-plugins-1.6.1-8.oe1.src | level3 |  |  |
| gavl-1.4.0-14.oe1.src | level3 | gavl-1.4.0-14.oe1.aarch64 |  |
| gcab-1.1-4.oe1.src | level3 | gcab-1.1-4.oe1.aarch64 |  |
| gflags-2.1.2-9.oe1.src | level3 | gflags-2.1.2-9.oe1.aarch64 |  |
| glew-2.1.0-3.oe1.src | level3 | glew-2.1.0-3.oe1.aarch64 |  |
| gnome-abrt-1.2.6-10.oe1.src | level3 | gnome-abrt-1.2.6-10.oe1.aarch64 |  |
| gnome-autoar-0.2.3-4.oe1.src | level3 | gnome-autoar-0.2.3-4.oe1.aarch64 |  |
| gnome-bluetooth-3.28.2-3.oe1.src | level3 | gnome-bluetooth-3.28.2-3.oe1.aarch64 |  |
| gnome-keyring-3.28.2-3.oe1.src | level3 |  |  |
| gnome-menus-3.13.3-12.oe1.src | level3 | gnome-menus-3.13.3-12.oe1.aarch64 |  |
| golang-github-vishvananda-netns-0-2.oe1.src | level3 |  |  |
| grilo-0.3.9-3.oe1.src | level3 | grilo-0.3.9-3.oe1.aarch64 |  |
| gsound-1.0.2-8.oe1.src | level3 | gsound-1.0.2-8.oe1.aarch64 |  |
| gspell-1.8.1-3.oe1.src | level3 | gspell-1.8.1-3.oe1.aarch64 |  |
| gstreamer1-plugins-good-1.14.4-3.oe1.src | level3 | gstreamer1-plugins-good-1.14.4-3.oe1.aarch64  |  |
| gstreamer1-plugins-good-1.14.4-3.oe1.src | level3 | gstreamer1-plugins-good-gtk-1.14.4-3.oe1.aarch64 |  |
| gtk-doc-1.29-4.oe1.src | level3 |  |  |
| gtkmm30-3.22.2-4.oe1.src | level3 | gtkmm30-3.22.2-4.oe1.aarch64 |  |
| gtksourceview3-3.24.9-5.oe1.src | level3 | gtksourceview3-3.24.9-5.oe1.aarch64 |  |
| python-logutils-0.3.5-7.oe1.src | level3 |  |  |
| hiredis-0.13.3-11.oe1.src | level3 | hiredis-0.13.3-11.oe1.aarch64 |  |
| iSulad-2.0.0-20200321.032232.gitaec8336d.oe1.src | level3 | iSulad-2.0.0-20200321.032232.gitaec8336d.oe1.aarch64 |  |
| ibus-table-1.9.21-3.oe1.src | level3 | ibus-table-1.9.21-3.oe1.noarch |  |
| jbig2dec-0.16-2.oe1.src | level3 | jbig2dec-0.16-2.oe1.aarch64 |  |
| jemalloc-5.1.0-3.oe1.src | level3 | jemalloc-5.1.0-3.oe1.aarch64 |  |
| jimtcl-0.78-2.oe1.src | level3 | jimtcl-0.78-2.oe1.aarch64 |  |
| jomolhari-fonts-0.003-28.oe1.src | level3 |  |  |
| jq-1.5-17.oe1.src | level3 | jq-1.5-17.oe1.aarch64 |  |
| julietaula-montserrat-fonts-7.200-5.oe1.src | level3 |  |  |
| kde-settings-29.1-3.oe1.src | level3 |  |  |
| keybinder3-0.3.2-8.oe1.src | level3 | keybinder3-0.3.2-8.oe1.aarch64 |  |
| khmeros-fonts-5.0-27.oe1.src | level3 |  |  |
| kyotocabinet-1.2.77-4.oe1.src | level3 | kyotocabinet-1.2.77-4.oe1.aarch64 |  |
| lato-fonts-2.015-8.oe1.src | level3 |  |  |
| resource-agents-4.2.0-2.oe1.src | level3 |  |  |
| libXxf86dga-1.1.4-16.oe1.src | level3 | libXxf86dga-1.1.4-16.oe1.aarch64 |  |
| libaesgm-20090429-21.oe1.src | level3 | libaesgm-20090429-21.oe1.aarch64 |  |
| libatasmart-0.19-17.oe1.src | level3 | libatasmart-0.19-17.oe1.aarch64 |  |
| libavc1394-0.5.4-12.oe1.src | level3 | libavc1394-0.5.4-12.oe1.aarch64 |  |
| libcdio-paranoia-10.2+2.0.0-2.oe1.src | level3 | libcdio-paranoia-10.2+2.0.0-2.oe1.aarch64 |  |
| libecap-1.0.1-4.oe1.src | level3 | libecap-1.0.1-4.oe1.aarch64 |  |
| libfprint-0.8.2-3.oe1.src | level3 | libfprint-0.8.2-3.oe1.aarch64 |  |
| libgdither-0.6-20.oe1.src | level3 | libgdither-0.6-20.oe1.aarch64 |  |
| libgnomeui-2.24.5-21.oe1.src | level3 | libgnomeui-2.24.5-21.oe1.aarch64 |  |
| libgovirt-0.3.4-10.oe1.src | level3 | libgovirt-0.3.4-10.oe1.aarch64 |  |
| libhangul-0.1.0-20.oe1.src | level3 | libhangul-0.1.0-20.oe1.aarch64 |  |
| libhbaapi-2.2.9-1.oe1.src | level3 | libhbaapi-2.2.9-1.oe1.aarch64 |  |
| libhugetlbfs-2.20-14.oe1.src | level3 | libhugetlbfs-2.20-14.oe1.aarch64 |  |
| libiec61883-1.2.0-22.oe1.src | level3 | libiec61883-1.2.0-22.oe1.aarch64 |  |
| libijs-0.35-9.oe1.src | level3 | libijs-0.35-9.oe1.aarch64 |  |
| libkcapi-1.1.5-2.oe1.src | level3 | libkcapi-1.1.5-2.oe1.aarch64 |  |
| libksba-1.3.5-12.oe1.src | level3 | libksba-1.3.5-12.oe1.aarch64 |  |
| libmaxminddb-1.2.0-7.oe1.src | level3 | libmaxminddb-1.2.0-7.oe1.aarch64 |  |
| libmediaart-1.9.4-7.oe1.src | level3 | libmediaart-1.9.4-7.oe1.aarch64 |  |
| libmodman-2.0.1-19.oe1.src | level3 | libmodman-2.0.1-19.oe1.aarch64 |  |
| libmypaint-1.3.0-10.oe1.src | level3 | libmypaint-1.3.0-10.oe1.aarch64 |  |
| libndp-1.7-3.oe1.src | level3 | libndp-1.7-3.oe1.aarch64 |  |
| libnetfilter_cthelper-1.0.0-15.oe1.src | level3 | libnetfilter_cthelper-1.0.0-15.oe1.aarch64 |  |
| libnetfilter_cttimeout-1.0.0-13.oe1.src | level3 | libnetfilter_cttimeout-1.0.0-13.oe1.aarch64 |  |
| libnetfilter_queue-1.0.2-13.oe1.src | level3 | libnetfilter_queue-1.0.2-13.oe1.aarch64 |  |
| libnfs-1.11.0-4.oe1.src | level3 | libnfs-1.11.0-4.oe1.aarch64 |  |
| libosinfo-1.2.0-9.oe1.src | level3 | libosinfo-1.2.0-9.oe1.aarch64 |  |
| libpeas-1.22.0-10.oe1.src | level3 | libpeas-1.22.0-10.oe1.aarch64  |  |
| libpeas-1.22.0-10.oe1.src | level3 | libpeas-devel-1.22.0-10.oe1.aarch64 |  |
| libpfm-4.10.1-7.oe1.src | level3 | libpfm-4.10.1-7.oe1.aarch64 |  |
| libquvi-0.9.4-16.oe1.src | level3 | libquvi-0.9.4-16.oe1.aarch64 |  |
| librdkafka-0.11.4-3.oe1.src | level3 | librdkafka-0.11.4-3.oe1.aarch64 |  |
| librelp-1.2.16-3.oe1.src | level3 | librelp-1.2.16-3.oe1.aarch64 |  |
| libreswan-3.25-5.oe1.src | level3 |  |  |
| libsass-3.5.4-4.oe1.src | level3 | libsass-3.5.4-4.oe1.aarch64 |  |
| libshout-2.2.2-21.oe1.src | level3 | libshout-2.2.2-21.oe1.aarch64 |  |
| libsigsegv-2.11-10.oe1.src | level3 | libsigsegv-2.11-10.oe1.aarch64 |  |
| libsmi-0.4.8-24.oe1.src | level3 | libsmi-0.4.8-24.oe1.aarch64 |  |
| libspectre-0.2.8-8.oe1.src | level3 | libspectre-0.2.8-8.oe1.aarch64 |  |
| sphinx-2.2.11-1.oe1.src | level3 | libsphinxclient-2.2.11-1.oe1.aarch64 |  |
| libspiro-20150131-10.oe1.src | level3 | libspiro-20150131-10.oe1.aarch64 |  |
| libstemmer-0-12.oe1.src | level3 | libstemmer-0-12.oe1.aarch64 |  |
| libtimezonemap-0.4.5.1-6.oe1.src | level3 | libtimezonemap-0.4.5.1-6.oe1.aarch64 |  |
| libvirt-glib-1.0.0-8.oe1.src | level1 | libvirt-glib-1.0.0-8.oe1.aarch64 |  |
| libwebsockets-2.4.2-3.oe1.src | level3 | libwebsockets-2.4.2-3.oe1.aarch64 |  |
| libwmf-0.2.12-2.oe1.src | level3 | libwmf-0.2.12-2.oe1.aarch64 |  |
| libwnck3-3.31.4-3.oe1.src | level3 | libwnck3-3.31.4-3.oe1.aarch64 |  |
| libxmlb-0.1.13-2.oe1.src | level3 | libxmlb-0.1.13-2.oe1.aarch64 |  |
| lksctp-tools-1.0.16-11.oe1.src | level3 | lksctp-tools-1.0.16-11.oe1.aarch64 |  |
| lldpad-1.0.1-13.oe1.src | level3 | lldpad-1.0.1-13.oe1.aarch64 |  |
| logwatch-7.5.2-2.oe1.src | level3 |  |  |
| lua-socket-3.0-0.19.oe1.src | level3 |  |  |
| lzop-1.04-1.oe1.src | level3 |  |  |
| mcpp-2.7.2-25.oe1.src | level3 | mcpp-2.7.2-25.oe1.aarch64 |  |
| memcached-1.5.10-4.oe1.src | level3 |  |  |
| metacity-3.30.1-2.oe1.src | level3 | metacity-3.30.1-2.oe1.aarch64 |  |
| mobile-broadband-provider-info-20190116-1.oe1.src | level3 |  |  |
| mozjs52-52.9.0-5.oe1.src | level3 | mozjs52-52.9.0-5.oe1.aarch64 |  |
| mtx-1.3.12-21.oe1.src | level3 |  |  |
| mutter-3.30.1-7.oe1.src | level3 | mutter-3.30.1-7.oe1.aarch64 |  |
| nilfs-utils-2.2.7-6.oe1.src | level3 | nilfs-utils-2.2.7-6.oe1.aarch64  |  |
| nilfs-utils-2.2.7-6.oe1.src | level3 | nilfs-utils-devel-2.2.7-6.oe1.aarch64 |  |
| nodejs-10.11.0-1.oe1.src | level3 |  |  |
| nototools-0-1.20190712.git9c4375f.oe1.src | level3 |  |  |
| npth-1.5-7.oe1.src | level3 | npth-1.5-7.oe1.aarch64 |  |
| nss_nis-3.0-8.oe1.src | level3 | nss_nis-3.0-8.oe1.aarch64 |  |
| ntp-4.2.8p13-5.oe1.src | level3 |  |  |
| oniguruma-6.9.0-2.oe1.src | level3 | oniguruma-6.9.0-2.oe1.aarch64 |  |
| open-iscsi-2.0.876-18.oe1.src | level3 | open-iscsi-2.0.876-18.oe1.aarch64 |  |
| open-isns-0.97-12.oe1.src | level3 | open-isns-0.97-12.oe1.aarch64 |  |
| openEuler-repos-1.0-2.5.oe1.src | level3 |  |  |
| openal-soft-1.18.2-8.oe1.src | level3 | openal-soft-1.18.2-8.oe1.aarch64 |  |
| opensm-3.3.20-11.oe1.src | level3 | opensm-3.3.20-11.oe1.aarch64 |  |
| opusfile-0.11-2.oe1.src | level3 | opusfile-0.11-2.oe1.aarch64 |  |
| osc-0.163.0-237.1.2.oe1.src | level3 |  |  |
| paktype-naqsh-fonts-4.1-11.oe1.src | level3 |  |  |
| paktype-naskh-basic-fonts-4.1-12.oe1.src | level3 |  |  |
| paktype-tehreer-fonts-4.1-11.oe1.src | level3 |  |  |
| papi-5.6.0-8.oe1.src | level3 | papi-5.6.0-8.oe1.aarch64 |  |
| paratype-pt-sans-fonts-20141121-10.oe1.src | level3 |  |  |
| pcaudiolib-1.1-3.oe1.src | level3 | pcaudiolib-1.1-3.oe1.aarch64 |  |
| perl-Algorithm-Diff-1.1903-14.oe1.src | level3 |  |  |
| perl-Archive-Zip-1.64-3.oe1.src | level3 |  |  |
| perl-CGI-4.46-1.oe1.src | level3 |  |  |
| perl-CPAN-Meta-YAML-0.018-420.oe1.src | level3 |  |  |
| perl-Capture-Tiny-0.48-4.oe1.src | level3 |  |  |
| perl-Carp-Clan-6.06-11.oe1.src | level3 |  |  |
| perl-Class-Method-Modifiers-2.12-12.oe1.src | level3 |  |  |
| perl-Compress-Raw-Bzip2-2.081-8.oe1.src | level3 |  |  |
| perl-DBD-MySQL-4.046-6.oe1.src | level3 |  |  |
| perl-Digest-SHA3-1.04-4.oe1.src | level3 |  |  |
| perl-Email-Date-Format-1.005-12.oe1.src | level3 |  |  |
| perl-Env-1.04-397.oe1.src | level3 |  |  |
| perl-Error-0.17026-4.oe1.src | level3 |  |  |
| perl-ExtUtils-CBuilder-0.280230-418.oe1.src | level3 |  |  |
| perl-ExtUtils-Config-0.008-19.oe1.src | level3 |  |  |
| perl-File-BaseDir-0.08-6.oe1.src | level3 |  |  |
| perl-File-HomeDir-1.004-4.oe1.src | level3 |  |  |
| perl-Filter-Simple-0.94-1.oe1.src | level3 |  |  |
| perl-GD-2.71-1.oe1.src | level3 |  |  |
| perl-IO-String-1.08-34.oe1.src | level3 |  |  |
| perl-IPC-System-Simple-1.25-19.oe1.src | level3 |  |  |
| perl-MailTools-2.20-5.oe1.src | level3 |  |  |
| perl-Math-BigRat-0.2614-2.oe1.src | level3 |  |  |
| perl-Module-Build-0.42.24-12.oe1.src | level3 |  |  |
| perl-Module-Install-ReadmeFromPod-0.30-12.oe1.src | level3 |  |  |
| perl-Module-Runtime-0.016-5.oe1.src | level3 |  |  |
| perl-Moo-2.003004-8.oe1.src | level3 |  |  |
| perl-Net-SMTP-SSL-1.04-8.oe1.src | level3 |  |  |
| perl-Params-Util-1.07-26.oe1.src | level3 |  |  |
| perl-Pod-Escapes-1.07-419.oe1.src | level3 |  |  |
| perl-Pod-Perldoc-3.28-3.oe1.src | level3 |  |  |
| perl-Software-License-0.103013-7.oe1.src | level3 |  |  |
| perl-Sub-Exporter-0.987-18.oe1.src | level3 |  |  |
| perl-Sub-Install-0.928-18.oe1.src | level3 |  |  |
| perl-Term-Cap-1.17-510.oe1.src | level3 |  |  |
| perl-Thread-Queue-3.13-3.oe1.src | level3 |  |  |
| perl-Try-Tiny-0.30-5.oe1.src | level3 |  |  |
| perl-Unicode-EastAsianWidth-1.33-15.oe1.src | level3 |  |  |
| perl-XML-SAX-Base-1.09-7.oe1.src | level3 |  |  |
| perl-XML-Simple-2.25-4.oe1.src | level3 |  |  |
| perl-YAML-LibYAML-0.74-2.oe1.src | level3 |  |  |
| perl-YAML-Tiny-1.73-4.oe1.src | level3 |  |  |
| perl-bignum-0.50-4.oe1.src | level3 |  |  |
| perl-common-sense-3.7.4-14.oe1.src | level3 |  |  |
| pkcs11-helper-1.25.1-1.oe1.src | level3 | pkcs11-helper-1.25.1-1.oe1.aarch64 |  |
| podman-0.10.1-3.oe1.src | level3 |  |  |
| poly2tri-0.0-19.oe1.src | level3 | poly2tri-0.0-19.oe1.aarch64 |  |
| poppler-data-0.4.9-4.oe1.src | level3 |  |  |
| ppp-2.4.7-29.oe1.src | level3 |  |  |
| publicsuffix-list-20180723-3.oe1.src | level3 |  |  |
| pyorbit-2.24.0-28.oe1.src | level3 |  |  |
| python-SecretStorage-2.3.1-11.oe1.src | level3 |  |  |
| python-asn1crypto-0.24.0-8.oe1.src | level3 |  |  |
| python-atomicwrites-1.1.5-13.oe1.src | level3 |  |  |
| python-click-7.0-1.oe1.src | level3 |  |  |
| python-dns-1.15.0-10.oe1.src | level3 |  |  |
| python-ethtool-0.14-2.oe1.src | level3 |  |  |
| python-inotify-0.9.6-16.oe1.src | level3 |  |  |
| python-jsonschema-2.6.0-6.oe1.src | level3 |  |  |
| python-jwt-1.7.1-2.oe1.src | level3 |  |  |
| python-packaging-17.1-2.oe1.src | level3 |  |  |
| python-paramiko-2.4.1-7.oe1.src | level3 |  |  |
| python-paste-2.0.3-10.oe1.src | level3 |  |  |
| python-py-1.5.4-4.oe1.src | level3 |  |  |
| python-reportlab-3.4.0-10.oe1.src | level3 |  |  |
| python-schedutils-0.6-5.oe1.src | level3 |  |  |
| python-semantic_version-2.6.0-9.oe1.src | level3 |  |  |
| python-sqlalchemy-1.2.11-2.oe1.src | level3 |  |  |
| python-webencodings-0.5.1-7.oe1.src | level3 |  |  |
| python-wheel-0.31.1-4.oe1.src | level3 |  |  |
| python-IPy-0.81-26.oe1.src | level3 |  |  |
| python-apipkg-1.5-2.oe1.src | level3 |  |  |
| python-augeas-0.5.0-16.oe1.src | level3 |  |  |
| python-bcrypt-3.1.4-7.oe1.src | level3 |  |  |
| python-beautifulsoup4-4.6.3-2.oe1.src | level3 |  |  |
| python-configobj-5.0.6-15.oe1.src | level3 |  |  |
| python-configshell-1.1.27-1.oe1.src | level3 |  |  |
| python-construct-2.5.1-19.oe1.src | level3 |  |  |
| python-docopt-0.6.2-11.oe1.src | level3 |  |  |
| python-entrypoints-0.2.3-10.oe1.src | level3 |  |  |
| python-fixtures-3.0.0-12.oe1.src | level3 |  |  |
| python-futures-3.1.1-5.oe1.src | level3 |  |  |
| python-gflags-2.0-16.oe1.src | level3 |  |  |
| python-imagesize-1.0.0-4.oe1.src | level3 |  |  |
| python-iso8601-0.1.11-2.oe1.src | level3 |  |  |
| python-itsdangerous-1.1.0-1.oe1.src | level3 |  |  |
| python-linecache2-1.0.0-18.oe1.src | level3 |  |  |
| python-linux-procfs-0.5.1-8.oe1.src | level3 |  |  |
| python-mimeparse-1.6.0-8.oe1.src | level3 |  |  |
| python-more-itertools-4.1.0-5.oe1.src | level3 |  |  |
| python-mysqlclient-1.3.12-7.oe1.src | level3 |  |  |
| python-olefile-0.46-2.oe1.src | level3 |  |  |
| python-pecan-1.3.2-5.oe1.src | level3 |  |  |
| python-pid-2.1.1-12.oe1.src | level3 |  |  |
| python-pluggy-0.6.0-6.oe1.src | level3 |  |  |
| python-polib-1.1.0-2.oe1.src | level3 |  |  |
| python-psutil-5.4.3-7.oe1.src | level3 |  |  |
| python-pydbus-0.6.0-10.oe1.src | level3 |  |  |
| python-pynacl-1.2.1-4.oe1.src | level3 |  |  |
| pyparted-3.11.0-18.oe1.src | level3 |  |  |
| python-pytest-fixture-config-1.2.11-6.oe1.src | level3 |  |  |
| python-pytest-shutil-1.2.6-6.oe1.src | level3 |  |  |
| python-simplegeneric-0.8.1-11.oe1.src | level3 |  |  |
| python-snowballstemmer-1.2.1-8.oe1.src | level3 |  |  |
| python-sphinx-theme-alabaster-0.7.11-6.oe1.src | level3 |  |  |
| python-sphinx_rtd_theme-0.4.1-2.oe1.src | level3 |  |  |
| python-sphinxcontrib-websupport-1.0.1-11.oe1.src | level3 |  |  |
| python-tempita-0.5.1-21.oe1.src | level3 |  |  |
| python2-typing-3.6.2-4.oe1.src | level3 |  |  |
| python-unittest2-1.1.0-16.oe1.src | level3 |  |  |
| python-urwid-2.0.1-5.oe1.src | level3 |  |  |
| python-waitress-1.1.0-5.oe1.src | level3 |  |  |
| python-webtest-2.0.30-2.oe1.src | level3 |  |  |
| python-whoosh-2.7.4-13.oe1.src | level3 |  |  |
| python-zope-event-4.2.0-12.oe1.src | level3 |  |  |
| python-meh-0.47-4.oe1.src | level3 |  |  |
| python-rpm-generators-9-1.oe1.src | level3 |  |  |
| rust-packaging-10-1.oe1.src | level3 |  |  |
| python-varlink-27.1.1-2.oe1.src | level3 |  |  |
| qpdf-8.4.2-2.oe1.src | level3 | qpdf-8.4.2-2.oe1.aarch64 |  |
| qrencode-4.0.2-2.oe1.src | level3 | qrencode-4.0.2-2.oe1.aarch64 |  |
| qt-mobility-1.2.2-2.oe1.src | level3 | qt-mobility-1.2.2-2.oe1.aarch64 |  |
| qt5-qt3d-5.11.1-3.oe1.src | level3 | qt5-qt3d-5.11.1-3.oe1.aarch64 |  |
| qt5-qtgraphicaleffects-5.11.1-4.oe1.src | level3 |  |  |
| qt5-qtimageformats-5.11.1-5.oe1.src | level3 |  |  |
| qt5-qtquickcontrols-5.11.1-4.oe1.src | level3 |  |  |
| qt5-qtserialbus-5.11.1-5.oe1.src | level3 | qt5-qtserialbus-5.11.1-5.oe1.aarch64 |  |
| quota-4.05-1.oe1.src | level3 |  |  |
| re2-20160401-8.oe1.src | level3 | re2-20160401-8.oe1.aarch64 |  |
| recode-3.6-50.oe1.src | level3 | recode-3.6-50.oe1.aarch64 |  |
| rpmdevtools-8.10-8.oe1.src | level3 |  |  |
| rtkit-0.11-26.oe1.src | level3 |  |  |
| rubygem-bundler-1.16.1-5.oe1.src | level3 |  |  |
| rubygem-gem2rpm-1.0.1-1.oe1.src | level3 |  |  |
| rubygem-rack-2.0.4-1.oe1.src | level3 |  |  |
| sblim-sfcCommon-1.0.1-13.oe1.src | level3 | sblim-sfcCommon-1.0.1-13.oe1.aarch64 |  |
| scap-security-guide-0.1.39-4.oe1.src | level3 |  |  |
| scsi-target-utils-1.0.79-1.oe1.src | level3 |  |  |
| sgpio-1.2.1-2.oe1.src | level3 |  |  |
| sil-abyssinica-fonts-1.200-17.oe1.src | level3 |  |  |
| sil-scheherazade-fonts-2.100-9.oe1.src | level3 |  |  |
| sleuthkit-4.6.7-2.oe1.src | level3 | sleuthkit-4.6.7-2.oe1.aarch64 |  |
| sombok-2.4.0-9.oe1.src | level3 | sombok-2.4.0-9.oe1.aarch64 |  |
| sox-14.4.2.0-26.oe1.src | level3 | sox-14.4.2.0-26.oe1.aarch64 |  |
| spice-0.14.1-1.oe1.src | level3 | spice-server-0.14.1-1.oe1.aarch64 |  |
| spice-protocol-0.12.14-3.oe1.src | level3 |  |  |
| strace-5.0-2.oe1.src | level3 |  |  |
| sudo-1.8.27-4.oe1.src | level3 | sudo-1.8.27-4.oe1.aarch64 |  |
| supermin-5.1.19-10.oe1.src | level3 |  |  |
| switcheroo-control-1.1-7.oe1.src | level3 |  |  |
| tcp_wrappers-7.6-96.oe1.src | level3 | tcp_wrappers-7.6-96.oe1.aarch64 |  |
| thai-scalable-fonts-0.6.5-5.oe1.src | level3 |  |  |
| thin-provisioning-tools-0.7.6-5.oe1.src | level3 |  |  |
| tibetan-machine-uni-fonts-1.901-23.oe1.src | level3 |  |  |
| tinycdb-0.78-11.oe1.src | level3 | tinycdb-0.78-11.oe1.aarch64 |  |
| tinyxml2-6.0.0-5.oe1.src | level3 | tinyxml2-6.0.0-5.oe1.aarch64 |  |
| tix-8.4.3-26.oe1.src | level3 | tix-8.4.3-26.oe1.aarch64 |  |
| trace-cmd-2.7-4.oe1.src | level3 |  |  |
| uchardet-0.0.6-1.oe1.src | level3 | uchardet-0.0.6-1.oe1.aarch64 |  |
| usermode-1.112-8.oe1.src | level3 |  |  |
| varnish-6.0.0-4.oe1.src | level3 | varnish-6.0.0-4.oe1.aarch64 |  |
| virglrenderer-0.7.0-1.oe1.src | level3 | virglrenderer-0.7.0-1.oe1.aarch64 |  |
| virt-what-1.18-8.oe1.src | level3 |  |  |
| wpa_supplicant-2.6-26.oe1.src | level3 |  |  |
| wqy-microhei-fonts-0.2.0-0.24.oe1.src | level3 |  |  |
| wqy-zenhei-fonts-0.9.46-21.oe1.src | level3 |  |  |
| xdg-user-dirs-0.17-3.oe1.src | level3 |  |  |
| xmms-1.2.11-39.20071117cvs.oe1.src | level3 | xmms-1.2.11-39.20071117cvs.oe1.aarch64 |  |
| xorg-x11-drv-libinput-0.28.0-5.oe1.src | level3 | xorg-x11-drv-libinput-0.28.0-5.oe1.aarch64 |  |
| xorg-x11-xbitmaps-1.1.1-16.oe1.src | level3 |  |  |
| xsane-0.999-29.oe1.src | level3 | xsane-0.999-29.oe1.aarch64 |  |
| yelp-3.34.0-1.oe1.src | level3 | yelp-3.34.0-1.oe1.aarch64 |  |
| zenity-3.30.0-2.oe1.src | level3 |  |  |
| GeoIP-GeoLite-data-2018.06-3.oe1.src | level3 |  |  |
| NetworkManager-libreswan-1.2.4-8.oe1.src | level3 | NetworkManager-libreswan-1.2.4-8.oe1.aarch64  |  |
| NetworkManager-libreswan-1.2.4-8.oe1.src | level3 | NetworkManager-libreswan-gnome-1.2.4-8.oe1.aarch64 |  |
| SDL2-2.0.8-9.oe1.src | level1 | SDL2-2.0.8-9.oe1.aarch64 |  |
| aalib-1.4.0-1.oe1.src | level3 | aalib-1.4.0-1.oe1.aarch64 |  |
| acpid-2.0.30-4.oe1.src | level3 |  |  |
| alsa-firmware-1.0.29-10.oe1.src | level3 |  |  |
| alsa-plugins-1.1.6-5.oe1.src | level3 | alsa-plugins-1.1.6-5.oe1.aarch64 |  |
| alsa-utils-1.1.6-8.oe1.src | level3 |  |  |
| anaconda-user-help-26.1-9.oe1.src | level3 |  |  |
| apache2-mod_xforward-0.6-lp151.1.2.oe1.src | level3 |  |  |
| atlas-3.10.3-9.oe1.src | level3 | atlas-3.10.3-9.oe1.aarch64 |  |
| audiofile-0.3.6-24.oe1.src | level3 | audiofile-0.3.6-24.oe1.aarch64 |  |
| autoconf-archive-2018.03.13-3.oe1.src | level3 |  |  |
| autofs-5.1.4-23.oe1.src | level3 |  |  |
| basesystem-12-1.oe1.src | level3 |  |  |
| blivet-gui-2.1.11-1.oe1.src | level3 | blivet-gui-2.1.11-1.oe1.noarch |  |
| brasero-3.12.2-8.oe1.src | level3 | brasero-3.12.2-8.oe1.aarch64 |  |
| bridge-utils-1.6-4.oe1.src | level3 |  |  |
| ccid-1.4.29-4.oe1.src | level3 | ccid-1.4.29-4.oe1.aarch64 |  |
| cdrdao-1.2.3-36.oe1.src | level3 |  |  |
| celt051-0.5.1.3-18.oe1.src | level3 | celt051-0.5.1.3-18.oe1.aarch64 |  |
| cifs-utils-6.8-5.oe1.src | level3 |  |  |
| cim-schema-2.43.0-8.oe1.src | level3 |  |  |
| cloud-utils-0.31-1.oe1.src | level3 |  |  |
| clutter-gst2-2.0.18-6.oe1.src | level3 | clutter-gst2-2.0.18-6.oe1.aarch64 |  |
| color-filesystem-1-14.oe1.src | level3 |  |  |
| conmon-2.0.2-2.oe1.src | level3 |  |  |
| container-exception-logger-1.0.3-2.oe1.src | level3 |  |  |
| container-selinux-2.73-3.gitd7a3f33.oe1.src | level3 |  |  |
| containernetworking-plugins-0.8.2-3.git485be65.oe1.src | level3 |  |  |
| copy-jdk-configs-3.7-3.oe1.src | level3 |  |  |
| crda-3.18_2018.05.31-6.oe1.src | level3 | crda-3.18_2018.05.31-6.oe1.aarch64 |  |
| ctags-5.8-27.oe1.src | level3 |  |  |
| cups-pk-helper-0.2.6-8.oe1.src | level3 |  |  |
| dbxtool-8-10.oe1.src | level3 |  |  |
| debootstrap-1.0.109-3.oe1.src | level3 |  |  |
| dejagnu-1.6.1-5.oe1.src | level3 |  |  |
| djvulibre-3.5.27-12.oe1.src | level3 | djvulibre-3.5.27-12.oe1.aarch64 |  |
| dkms-2.6.1-5.oe1.src | level3 |  |  |
| dnsmasq-2.79-11.oe1.src | level3 |  |  |
| dnssec-trigger-0.15-9.oe1.src | level3 |  |  |
| docbook-style-dsssl-1.79-27.oe1.src | level3 |  |  |
| docker-engine-18.09.0-101.oe1.src | level3 |  |  |
| docker-runc-1.0.0.rc3-103.oe1.src | level3 |  |  |
| dovecot-2.3.3-5.oe1.src | level3 | dovecot-2.3.3-5.oe1.aarch64 |  |
| dvd+rw-tools-7.1-31.oe1.src | level3 |  |  |
| edac-utils-0.16-20.oe1.src | level3 | edac-utils-0.16-20.oe1.aarch64 |  |
| egl-wayland-1.1.0-0.4.oe1.src | level3 | egl-wayland-1.1.0-0.4.oe1.aarch64 |  |
| eglexternalplatform-1.1-0.2.oe1.src | level3 |  |  |
| elinks-0.12-1.oe1.src | level3 |  |  |
| emacs-auctex-12.1-8.oe1.src | level3 |  |  |
| eog-3.28.4-2.oe1.src | level3 | eog-3.28.4-2.oe1.aarch64 |  |
| epydoc-3.0.1-11.oe1.src | level3 |  |  |
| evince-3.30.1-3.oe1.src | level3 | evince-3.30.1-3.oe1.aarch64 |  |
| fakeroot-1.23-2.oe1.src | level3 | fakeroot-1.23-2.oe1.aarch64 |  |
| fdupes-1.6.1-6.oe1.src | level3 |  |  |
| festival-freebsoft-utils-0.10-15.oe1.src | level3 |  |  |
| file-roller-3.30.1-2.oe1.src | level3 | file-roller-3.30.1-2.oe1.aarch64  |  |
| file-roller-3.30.1-2.oe1.src | level3 | file-roller-nautilus-3.30.1-2.oe1.aarch64 |  |
| flatpak-1.0.3-2.oe1.src | level3 | flatpak-1.0.3-2.oe1.aarch64 |  |
| flex-2.6.1-13.oe1.src | level3 |  |  |
| fros-1.1-18.oe1.src | level3 |  |  |
| fwupd-1.2.9-2.oe1.src | level3 | fwupd-1.2.9-2.oe1.aarch64 |  |
| fxload-2008_10_13-14.oe1.src | level3 |  |  |
| galera-25.3.26-3.oe1.src | level3 | galera-25.3.26-3.oe1.aarch64 |  |
| gfs2-utils-3.2.0-5.oe1.src | level3 |  |  |
| gl-manpages-1.1-17.oe1.src | level3 |  |  |
| gnome-color-manager-3.30.0-2.oe1.src | level3 | gnome-color-manager-3.30.0-2.oe1.aarch64 |  |
| gnome-contacts-3.30.1-2.oe1.src | level3 | gnome-contacts-3.30.1-2.oe1.aarch64 |  |
| gnome-doc-utils-0.20.10-17.oe1.src | level3 |  |  |
| gnome-getting-started-docs-3.30.0-2.oe1.src | level3 |  |  |
| gnome-icon-theme-3.12.0-12.oe1.src | level3 |  |  |
| gnome-packagekit-3.30.0-2.oe1.src | level3 | gnome-packagekit-3.30.0-2.oe1.aarch64 |  |
| gnome-python2-2.28.1-24.oe1.src | level3 | gnome-python2-2.28.1-24.oe1.aarch64 |  |
| gnome-session-3.30.1-3.oe1.src | level3 |  |  |
| gnome-themes-standard-3.27.90-3.oe1.src | level3 | gnome-themes-standard-3.27.90-3.oe1.aarch64 |  |
| gnome-user-docs-3.30.1-2.oe1.src | level3 |  |  |
| gnome-video-effects-0.4.3-5.oe1.src | level3 |  |  |
| gnu-efi-3.0.8-8.oe1.src | level3 |  |  |
| gnulib-0-27.20180720git.oe1.src | level3 |  |  |
| golang-github-coreos-go-iptables-0.4.0-2.oe1.src | level3 |  |  |
| golang-github-golang-sys-0-0.22.20200305git7c87d13.oe1.src | level3 |  |  |
| golang-github-russross-blackfriday-2.0.0-3.20191204git55d61fa.oe1.src | level3 |  |  |
| golang-github-vishvananda-netlink-0-0.18.oe1.src | level3 |  |  |
| google-noto-emoji-fonts-20180814-4.oe1.src | level3 |  |  |
| gperf-3.1-7.oe1.src | level3 |  |  |
| grubby-8.40-24.oe1.src | level3 |  |  |
| gsl-2.4-9.oe1.src | level3 | gsl-2.4-9.oe1.aarch64 |  |
| gssproxy-0.8.0-11.oe1.src | level3 |  |  |
| gstreamer-plugins-base-0.10.36-20.oe1.src | level3 | gstreamer-plugins-base-0.10.36-20.oe1.aarch64 |  |
| gupnp-igd-0.2.5-6.oe1.src | level3 | gupnp-igd-0.2.5-6.oe1.aarch64 |  |
| gutenprint-5.2.14-4.oe1.src | level3 | gutenprint-5.2.14-4.oe1.aarch64 |  |
| gv-3.7.4-19.oe1.src | level3 |  |  |
| haveged-1.9.8-2.oe1.src | level3 | haveged-1.9.8-2.oe1.aarch64 |  |
| hdf5-1.8.20-7.oe1.src | level3 | hdf5-1.8.20-7.oe1.aarch64 |  |
| hdparm-9.56-4.oe1.src | level3 |  |  |
| hexedit-1.2.13-15.oe1.src | level3 |  |  |
| hfsplus-tools-540.1.linux3-17.oe1.src | level3 |  |  |
| hunspell-en-0.201910.06-2.oe1.src | level3 |  |  |
| hwinfo-21.47-5.oe1.src | level3 | hwinfo-21.47-5.oe1.aarch64 |  |
| iSulad-img-2.0.0-20200318.002428.gitdbc56638.oe1.src | level3 |  |  |
| icoutils-0.32.3-4.oe1.src | level3 |  |  |
| iio-sensor-proxy-2.4-5.oe1.src | level3 |  |  |
| indent-2.2.11-27.oe1.src | level3 |  |  |
| iperf3-3.6-4.oe1.src | level3 | iperf3-3.6-4.oe1.aarch64 |  |
| ipvsadm-1.29-11.oe1.src | level3 |  |  |
| irqbalance-1.4.0-18.oe1.src | level3 |  |  |
| itstool-2.0.4-5.oe1.src | level3 |  |  |
| iw-5.0.1-1.oe1.src | level3 |  |  |
| jfsutils-1.1.15-14.oe1.src | level3 |  |  |
| kacst-fonts-2.0-22.oe1.src | level3 |  |  |
| kmod-kvdo-6.2.2.24-5.oe1.src | level3 |  |  |
| kyua-0.13-3.oe1.src | level3 |  |  |
| lapack-3.8.0-16.oe1.src | level3 | lapack-3.8.0-16.oe1.aarch64 |  |
| lcr-2.0.0-20200318.003237.gitcdd4f8ab.oe1.src | level3 | lcr-2.0.0-20200318.003237.gitcdd4f8ab.oe1.aarch64 |  |
| lftp-4.8.4-2.oe1.src | level3 | lftp-4.8.4-2.oe1.aarch64 |  |
| libXvMC-1.0.11-2.oe1.src | level3 | libXvMC-1.0.11-2.oe1.aarch64 |  |
| libatomic_ops-7.6.10-2.oe1.src | level3 | libatomic_ops-7.6.10-2.oe1.aarch64 |  |
| libclc-0.2.0-14.oe1.src | level3 |  |  |
| cmocka-1.1.5-2.oe1.src | level3 | libcmocka-1.1.5-2.oe1.aarch64 |  |
| libdvdnav-6.0.0-4.oe1.src | level3 | libdvdnav-6.0.0-4.oe1.aarch64 |  |
| libell-0.9-2.oe1.src | level3 | libell-0.9-2.oe1.aarch64 |  |
| libesmtp-1.0.6-18.oe1.src | level3 | libesmtp-1.0.6-18.oe1.aarch64 |  |
| libevhtp-1.2.16-3.oe1.src | level3 | libevhtp-1.2.16-3.oe1.aarch64 |  |
| libfabric-1.6.1-3.oe1.src | level3 | libfabric-1.6.1-3.oe1.aarch64 |  |
| libhbalinux-1.0.17-2.oe1.src | level3 | libhbalinux-1.0.17-2.oe1.aarch64 |  |
| libid3tag-0.15.1b-20.oe1.src | level3 | libid3tag-0.15.1b-20.oe1.aarch64 |  |
| libmemcached-1.0.18-16.oe1.src | level3 | libmemcached-1.0.18-16.oe1.aarch64 |  |
| libmpcdec-1.2.6-23.oe1.src | level3 | libmpcdec-1.2.6-23.oe1.aarch64 |  |
| libmspack-0.7-0.1.6.oe1.src | level3 | libmspack-0.7-0.1.6.oe1.aarch64 |  |
| libmtp-1.1.14-5.oe1.src | level3 | libmtp-1.1.14-5.oe1.aarch64 |  |
| libpng12-1.2.57-10.oe1.src | level3 | libpng12-1.2.57-10.oe1.aarch64 |  |
| libquvi-scripts-0.9.20131130-14.oe1.src | level3 |  |  |
| libsexy-0.1.11-35.oe1.src | level3 | libsexy-0.1.11-35.oe1.aarch64 |  |
| libsrtp-1.5.4-10.oe1.src | level3 | libsrtp-1.5.4-10.oe1.aarch64 |  |
| libusb-0.1.5-15.oe1.src | level3 | libusb-0.1.5-15.oe1.aarch64 |  |
| libva-2.5.0-1.oe1.src | level3 | libva-2.5.0-1.oe1.aarch64 |  |
| libvdpau-1.2-1.oe1.src | level3 | libvdpau-1.2-1.oe1.aarch64 |  |
| libvma-8.9.4-7.oe1.src | level3 | libvma-8.9.4-7.oe1.aarch64 |  |
| libwd-1.2.10-3.oe1.src | level3 | libwd-1.2.10-3.oe1.aarch64 |  |
| libx86emu-1.11-4.oe1.src | level3 | libx86emu-1.11-4.oe1.aarch64 |  |
| libzip-1.5.1-3.oe1.src | level2 | libzip-1.5.1-3.oe1.aarch64 |  |
| linux-firmware-20190815-4.oe1.src | level3 |  |  |
| linuxconsoletools-1.6.0-3.oe1.src | level3 |  |  |
| lrzsz-0.12.20-46.oe1.src | level3 |  |  |
| lsscsi-0.30-2.oe1.src | level3 |  |  |
| lua-expat-1.3.0-16.oe1.src | level3 |  |  |
| lua-json-1.3.2-13.oe1.src | level3 |  |  |
| lua-lpeg-1.0.2-2.oe1.src | level3 |  |  |
| lua-posix-33.3.1-12.oe1.src | level3 |  |  |
| lxsession-0.5.4-2.oe1.src | level3 |  |  |
| lynx-2.8.9-5.oe1.src | level3 |  |  |
| m2crypto-0.30.1-3.oe1.src | level3 |  |  |
| mac-robber-1.02-18.oe1.src | level3 |  |  |
| mallard-rng-1.0.3-4.oe1.src | level3 |  |  |
| mcstrans-0.3.4-17.oe1.src | level3 |  |  |
| meanwhile-1.1.1-1.oe1.src | level3 | meanwhile-1.1.1-1.oe1.aarch64 |  |
| mesa-libGLw-8.0.0-16.oe1.src | level3 | mesa-libGLw-8.0.0-16.oe1.aarch64 |  |
| mlocate-0.26-24.oe1.src | level3 |  |  |
| mod_http2-1.10.20-4.oe1.src | level3 |  |  |
| mokutil-0.4.0-1.oe1.src | level3 |  |  |
| mt-st-1.1-25.oe1.src | level3 |  |  |
| mtools-4.0.18-19.oe1.src | level3 |  |  |
| mvapich2-2.3-7.oe1.src | level3 | mvapich2-2.3-7.oe1.aarch64 |  |
| neon-0.30.2-9.oe1.src | level3 | neon-0.30.2-9.oe1.aarch64 |  |
| ninja-build-1.8.2-8.oe1.src | level3 |  |  |
| notification-daemon-3.20.0-7.oe1.src | level3 |  |  |
| numad-0.5-31.oe1.src | level3 |  |  |
| obs-server-2.10.1-lp151.23.3.oe1.src | level3 |  |  |
| openEuler-indexhtml-7-14.oe1.src | level3 |  |  |
| openEuler-release-20.03LTS-33.oe1.src | level3 |  |  |
| openhpi-3.8.0-7.oe1.src | level3 | openhpi-3.8.0-7.oe1.aarch64 |  |
| opensc-0.20.0-2.oe1.src | level3 | opensc-0.20.0-2.oe1.aarch64 |  |
| openssl-pkcs11-0.4.10-1.oe1.src | level3 | openssl-pkcs11-0.4.10-1.oe1.aarch64 |  |
| os-prober-1.74-12.oe1.src | level3 |  |  |
| osinfo-db-20180920-2.oe1.src | level3 |  |  |
| osinfo-db-tools-1.2.0-3.oe1.src | level3 |  |  |
| paps-0.6.8-45.oe1.src | level3 | paps-0.6.8-45.oe1.aarch64 |  |
| passwd-0.80-7.oe1.src | level3 |  |  |
| pax-3.4-34.oe1.src | level3 |  |  |
| perl-Authen-SASL-2.16-16.oe1.src | level3 |  |  |
| perl-B-Debug-1.26-4.oe1.src | level3 |  |  |
| perl-BSSolv-0.37-lp151.1.2.oe1.src | level3 |  |  |
| perl-Bit-Vector-7.4-14.oe1.src | level3 |  |  |
| perl-Business-ISBN-Data-20191107-2.oe1.src | level3 |  |  |
| perl-CPAN-2.27-3.oe1.src | level3 |  |  |
| perl-Class-Inspector-1.32-5.oe1.src | level3 |  |  |
| perl-Compress-Bzip2-2.26-10.oe1.src | level3 |  |  |
| perl-Config-General-2.63-1.oe1.src | level3 |  |  |
| perl-Config-Perl-V-0.30-5.oe1.src | level3 |  |  |
| perl-Crypt-DES-2.07-17.oe1.src | level3 |  |  |
| perl-DBD-SQLite-1.58-4.oe1.src | level3 |  |  |
| perl-DB_File-1.842-2.oe1.src | level3 |  |  |
| perl-Data-Dump-1.23-10.oe1.src | level3 |  |  |
| perl-Data-OptList-0.110-9.oe1.src | level3 |  |  |
| perl-Data-Section-0.200007-6.oe1.src | level3 |  |  |
| perl-Data-UUID-1.221-13.oe1.src | level3 |  |  |
| perl-Date-Calc-6.4-12.oe1.src | level3 |  |  |
| perl-Date-Manip-6.73-2.oe1.src | level3 |  |  |
| perl-Devel-GlobalDestruction-0.14-8.oe1.src | level3 |  |  |
| perl-Devel-Size-0.82-4.oe1.src | level3 |  |  |
| perl-Devel-Symdump-2.18-9.oe1.src | level3 |  |  |
| perl-Exporter-Tiny-1.002001-5.oe1.src | level3 |  |  |
| perl-ExtUtils-Helpers-0.026-13.oe1.src | level3 |  |  |
| perl-ExtUtils-InstallPaths-0.012-9.oe1.src | level3 |  |  |
| perl-Fedora-VSP-0.001-10.oe1.src | level3 |  |  |
| perl-File-Copy-Recursive-0.44-4.oe1.src | level3 |  |  |
| perl-File-DesktopEntry-0.22-11.oe1.src | level3 |  |  |
| perl-File-Fetch-0.56-4.oe1.src | level3 |  |  |
| perl-File-MimeInfo-0.29-3.oe1.src | level3 |  |  |
| perl-File-ReadBackwards-1.05-1.oe1.src | level3 |  |  |
| perl-File-Remove-1.58-2.oe1.src | level3 |  |  |
| perl-File-ShareDir-1.116-4.oe1.src | level3 |  |  |
| perl-File-Slurp-9999.19-22.oe1.src | level3 |  |  |
| perl-File-Sync-0.11-20.oe1.src | level3 |  |  |
| perl-GD-Barcode-1.15-30.oe1.src | level3 |  |  |
| perl-GSSAPI-0.28-26.oe1.src | level3 |  |  |
| perl-HTML-Tagset-3.20-37.oe1.src | level3 |  |  |
| perl-HTTP-Cookies-6.04-5.oe1.src | level3 |  |  |
| perl-HTTP-Negotiate-6.01-22.oe1.src | level3 |  |  |
| perl-IO-HTML-1.001-13.oe1.src | level3 |  |  |
| perl-IO-Multiplex-1.16-12.oe1.src | level3 |  |  |
| perl-IO-stringy-2.111-12.oe1.src | level3 |  |  |
| perl-IPC-SysV-2.07-5.oe1.src | level3 |  |  |
| perl-Import-Into-1.002005-2.oe1.src | level3 |  |  |
| perl-JSON-XS-3.04-6.oe1.src | level3 |  |  |
| perl-LWP-Protocol-https-6.07-7.oe1.src | level3 |  |  |
| perl-List-MoreUtils-XS-0.428-7.oe1.src | level3 |  |  |
| perl-Locale-Codes-3.58-2.oe1.src | level3 |  |  |
| perl-Locale-Maketext-1.28-1.oe1.src | level3 |  |  |
| perl-gettext-1.07-13.oe1.src | level3 |  |  |
| perl-MIME-Types-2.17-5.oe1.src | level3 |  |  |
| perl-MRO-Compat-0.13-9.oe1.src | level3 |  |  |
| perl-Mail-Sender-0.903-8.oe1.src | level3 |  |  |
| perl-Math-BigInt-FastCalc-0.500.700-4.oe1.src | level3 |  |  |
| perl-Module-Install-AuthorRequires-0.02-18.oe1.src | level3 |  |  |
| perl-Module-Install-GithubMeta-0.30-12.oe1.src | level3 |  |  |
| perl-Module-Install-ManifestSkip-0.24-13.oe1.src | level3 |  |  |
| perl-Module-Install-ReadmeMarkdownFromPod-0.04-10.oe1.src | level3 |  |  |
| perl-Module-Manifest-Skip-0.23-16.oe1.src | level3 |  |  |
| perl-Module-Package-0.30-22.oe1.src | level3 |  |  |
| perl-Mozilla-CA-20180117-6.oe1.src | level3 |  |  |
| perl-NTLM-1.09-20.oe1.src | level3 |  |  |
| perl-Net-SNMP-6.0.1-23.oe1.src | level3 |  |  |
| perl-Net-Server-2.009-6.oe1.src | level3 |  |  |
| perl-Package-Generator-1.106-14.oe1.src | level3 |  |  |
| perl-Parse-Yapp-1.21-5.oe1.src | level3 |  |  |
| perl-PerlIO-via-QuotedPrint-0.08-397.oe1.src | level3 |  |  |
| perl-Pod-Checker-1.73-398.oe1.src | level3 |  |  |
| perl-Pod-Coverage-0.23-15.oe1.src | level3 |  |  |
| perl-Role-Tiny-2.001004-2.oe1.src | level3 |  |  |
| perl-SGMLSpm-1.03ii-45.oe1.src | level3 |  |  |
| perl-SNMP_Session-1.13-20.oe1.src | level3 |  |  |
| perl-Socket-MsgHdr-0.05-5.oe1.src | level3 |  |  |
| perl-String-CRC32-1.7-4.oe1.src | level3 |  |  |
| perl-Sub-Exporter-Progressive-0.001013-8.oe1.src | level3 |  |  |
| perl-Sub-Quote-2.005001-4.oe1.src | level3 |  |  |
| perl-Sys-Virt-4.7.0-2.oe1.src | level3 |  |  |
| perl-Text-CharWidth-0.04-36.oe1.src | level3 |  |  |
| perl-Text-Diff-1.45-7.oe1.src | level3 |  |  |
| perl-Text-Glob-0.11-7.oe1.src | level3 |  |  |
| perl-Text-Template-1.53-4.oe1.src | level3 |  |  |
| perl-Text-WrapI18N-0.06-33.oe1.src | level3 |  |  |
| perl-Types-Serialiser-1.0-18.oe1.src | level3 |  |  |
| perl-Unicode-Collate-1.25-4.oe1.src | level3 |  |  |
| perl-Unicode-UTF8-0.62-8.oe1.src | level3 |  |  |
| perl-WWW-RobotRules-6.02-21.oe1.src | level3 |  |  |
| perl-XML-Catalog-1.03-14.oe1.src | level3 |  |  |
| perl-XML-LibXML-2.0132-5.oe1.src | level3 |  |  |
| perl-XML-TokeParser-0.05-28.oe1.src | level3 |  |  |
| perl-XML-XPath-1.42-6.oe1.src | level3 |  |  |
| perl-YAML-1.26-5.oe1.src | level3 |  |  |
| perl-experimental-0.020-2.oe1.src | level3 |  |  |
| perl-local-lib-2.000024-2.oe1.src | level3 |  |  |
| perl-inc-latest-0.500-12.oe1.src | level3 |  |  |
| perl-perlfaq-5.20180915-5.oe1.src | level3 |  |  |
| pesign-0.113-1.oe1.src | level3 |  |  |
| pigz-2.4-7.oe1.src | level3 |  |  |
| pinentry-1.1.0-5.oe1.src | level3 |  |  |
| pinfo-0.6.10-22.oe1.src | level3 |  |  |
| polkit-gnome-0.106-0.4.20170423gita0763a2.oe1.src | level3 |  |  |
| polkit-pkla-compat-0.1-17.oe1.src | level3 |  |  |
| portreserve-0.0.5-21.oe1.src | level3 |  |  |
| powertop-2.9-11.oe1.src | level3 |  |  |
| prefetch_tuning-1.0-2.oe1.src | level3 |  |  |
| protobuf-c-1.3.2-2.oe1.src | level3 | protobuf-c-1.3.2-2.oe1.aarch64 |  |
| psutils-1.23-16.oe1.src | level3 |  |  |
| pv-1.6.6-6.oe1.src | level3 |  |  |
| python-cached_property-1.5.1-1.oe1.src | level3 |  |  |
| python-cups-1.9.72-23.oe1.src | level3 |  |  |
| python-dmidecode-3.12.2-17.oe1.src | level3 |  |  |
| python-docker-4.0.2-1.oe1.src | level3 |  |  |
| python-dockerpty-0.4.1-1.oe1.src | level3 |  |  |
| python-gevent-1.3.6-2.oe1.src | level3 |  |  |
| python-jsonpatch-1.21-5.oe1.src | level3 |  |  |
| python-nose-1.3.7-26.oe1.src | level3 |  |  |
| python-ntplib-0.3.3-13.oe1.src | level3 |  |  |
| python-requests-ftp-0.3.1-15.oe1.src | level3 |  |  |
| python-ruamel-yaml-clib-0.1.2-1.oe1.src | level3 |  |  |
| python-ruamel-yaml-0.16.5-1.oe1.src | level3 |  |  |
| python-tqdm-4.28.1-1.oe1.src | level3 |  |  |
| python-zipp-0.5.1-1.oe1.src | level3 |  |  |
| python-alsa-1.1.6-1.oe1.src | level3 |  |  |
| python-backports-1.0-17.oe1.src | level3 |  |  |
| python-backports_abc-0.5-9.oe1.src | level3 |  |  |
| python-cherrypy-3.5.0-12.oe1.src | level3 |  |  |
| python-configparser-3.5.0b2-11.oe1.src | level3 |  |  |
| python-distro-1.3.0-5.oe1.src | level3 |  |  |
| fuse-python-0.2.1-24.oe1.src | level3 |  |  |
| python-junitxml-0.7-17.oe1.src | level3 |  |  |
| libvirt-python-5.5.0-1.oe1.src | level3 |  |  |
| python-lit-0.7.0-3.oe1.src | level3 |  |  |
| python-oauthlib-3.0.2-1.oe1.src | level3 |  |  |
| python-productmd-1.22-2.oe1.src | level3 |  |  |
| pyserial-3.4-2.oe1.src | level3 |  |  |
| pyxattr-0.6.1-1.oe1.src | level3 |  |  |
| pyxdg-0.26-2.oe1.src | level3 |  |  |
| python-repoze-lru-0.7-2.oe1.src | level3 |  |  |
| python-requests-file-1.4.3-9.oe1.src | level3 |  |  |
| rhnlib-2.8.11-3.oe1.src | level3 |  |  |
| python-texttable-1.4.0-2.oe1.src | level3 |  |  |
| python-urlgrabber-4.0.0-1.oe1.src | level3 |  |  |
| python-aniso8601-7.0.0-1.oe1.src | level3 |  |  |
| python-argcomplete-1.9.5-2.oe1.src | level3 |  |  |
| python-dict2xml-1.6.1-1.oe1.src | level3 |  |  |
| python-evdev-1.1.2-5.oe1.src | level3 |  |  |
| python-flask-restful-0.3.6-10.oe1.src | level3 |  |  |
| python-joblib-0.14.0-3.oe1.src | level3 |  |  |
| python-pandas-0.25.3-1.oe1.src | level3 | python3-pandas-0.25.3-1.oe1.aarch64 |  |
| pywbem-0.12.4-5.oe1.src | level3 |  |  |
| python-scikit-learn-0.20.4-2.oe1.src | level3 | python3-scikit-learn-0.20.4-2.oe1.aarch64 |  |
| python-scikit-optimize-0.5.2-1.oe1.src | level3 |  |  |
| python-simpleline-1.6-1.oe1.src | level3 |  |  |
| python-u-msgpack-python-2.5.0-2.oe1.src | level3 |  |  |
| python-xgboost-0.90-2.oe1.src | level3 | python3-xgboost-0.90-2.oe1.aarch64 |  |
| qhull-2015.2-7.oe1.src | level3 | qhull-2015.2-7.oe1.aarch64 |  |
| qt5-qtcanvas3d-5.11.1-3.oe1.src | level3 | qt5-qtcanvas3d-5.11.1-3.oe1.aarch64 |  |
| qt5-qtdoc-5.11.1-3.oe1.src | level3 |  |  |
| radvd-2.18-2.oe1.src | level3 |  |  |
| rcs-5.9.4-11.oe1.src | level3 |  |  |
| realmd-0.16.3-21.oe1.src | level3 |  |  |
| reiserfs-utils-3.6.27-2.oe1.src | level3 | reiserfs-utils-3.6.27-2.oe1.aarch64 |  |
| rubygem-asciidoctor-1.5.6.1-5.oe1.src | level3 |  |  |
| screen-4.6.2-6.oe1.src | level3 |  |  |
| scrub-2.5.2-14.oe1.src | level3 |  |  |
| setroubleshoot-plugins-3.3.9-8.oe1.src | level3 |  |  |
| sg3_utils-1.42-10.oe1.src | level3 | sg3_utils-1.42-10.oe1.aarch64 |  |
| sharutils-4.15.2-15.oe1.src | level3 |  |  |
| smp_utils-0.98-14.oe1.src | level3 | smp_utils-0.98-14.oe1.aarch64 |  |
| socat-1.7.3.2-8.oe1.src | level3 |  |  |
| softhsm-2.5.0-3.oe1.src | level3 | softhsm-2.5.0-3.oe1.aarch64 |  |
| sos-3.6-5.oe1.src | level3 |  |  |
| sound-theme-freedesktop-0.8-12.oe1.src | level3 |  |  |
| soundtouch-2.1.0-2.oe1.src | level3 | soundtouch-2.1.0-2.oe1.aarch64 |  |
| sscg-2.3.3-5.oe1.src | level3 |  |  |
| star-1.5.3-16.oe1.src | level3 |  |  |
| stunnel-5.48-3.oe1.src | level3 | stunnel-5.48-3.oe1.aarch64 |  |
| sysstat-12.1.6-2.oe1.src | level3 |  |  |
| telepathy-logger-0.8.2-12.oe1.src | level3 | telepathy-logger-0.8.2-12.oe1.aarch64 |  |
| timedatex-0.5-10.oe1.src | level3 |  |  |
| tmux-2.9a-1.oe1.src | level3 |  |  |
| tog-pegasus-2.14.1-48.oe1.src | level3 | tog-pegasus-2.14.1-48.oe1.aarch64  |  |
| tog-pegasus-2.14.1-48.oe1.src | level3 | tog-pegasus-devel-2.14.1-48.oe1.aarch64 |  |
| tpm-tools-1.3.9-6.oe1.src | level3 | tpm-tools-1.3.9-6.oe1.aarch64 |  |
| tpm2-abrmd-2.0.1-4.oe1.src | level3 | tpm2-abrmd-2.0.1-4.oe1.aarch64 |  |
| tpm2-tools-3.1.1-7.oe1.src | level3 |  |  |
| tracker-miners-2.1.5-6.oe1.src | level3 | tracker-miners-2.1.5-6.oe1.aarch64 |  |
| tuna-0.13.1-7.oe1.src | level3 |  |  |
| tuned-2.10.0-7.oe1.src | level3 |  |  |
| u2f-hidraw-policy-1.0.2-9.oe1.src | level3 |  |  |
| ucs-miscfixed-fonts-0.3-19.oe1.src | level3 |  |  |
| umockdev-0.11.3-3.oe1.src | level3 | umockdev-0.11.3-3.oe1.aarch64 |  |
| unicode-ucd-11.0.0-3.oe1.src | level3 |  |  |
| urlview-0.9-24.20131022git08767a.oe1.src | level3 |  |  |
| usb_modeswitch-2.5.2-4.oe1.src | level3 |  |  |
| usb_modeswitch-data-20170806-4.oe1.src | level3 |  |  |
| valgrind-3.13.0-29.oe1.src | level3 |  |  |
| vdo-6.2.0.298-12.oe1.src | level3 |  |  |
| vorbis-tools-1.4.0-30.oe1.src | level3 |  |  |
| vulkan-headers-1.1.92.0-2.oe1.src | level3 |  |  |
| wayland-protocols-1.18-1.oe1.src | level3 |  |  |
| words-3.0-32.oe1.src | level3 |  |  |
| xapian-core-1.4.9-2.oe1.src | level3 | xapian-core-1.4.9-2.oe1.aarch64 |  |
| xinetd-2.3.15-29.oe1.src | level3 |  |  |
| xmlsec1-1.2.29-1.oe1.src | level3 | xmlsec1-1.2.29-1.oe1.aarch64 |  |
| xmlstarlet-1.6.1-12.oe1.src | level3 |  |  |
| xorg-x11-drv-ati-19.1.0-2.oe1.src | level3 |  |  |
| xorg-x11-drv-dummy-0.3.8-1.oe1.src | level3 |  |  |
| xorg-x11-drv-evdev-2.10.6-4.oe1.src | level3 |  |  |
| xorg-x11-drv-fbdev-0.5.0-5.oe1.src | level3 |  |  |
| xorg-x11-drv-nouveau-1.0.15-7.oe1.src | level3 |  |  |
| xorg-x11-drv-v4l-0.3.0-1.oe1.src | level3 |  |  |
| xorg-x11-drv-wacom-0.36.1-6.oe1.src | level3 |  |  |
| xorg-x11-util-macros-1.19.2-4.oe1.src | level3 |  |  |
| yaml-cpp-0.6.3-1.oe1.src | level3 | yaml-cpp-0.6.3-1.oe1.aarch64 |  |
| yasm-1.3.0-9.oe1.src | level3 |  |  |
| yelp-tools-3.28.0-3.oe1.src | level3 |  |  |
| yp-tools-4.2.3-4.oe1.src | level3 |  |  |
| ypbind-2.6-3.oe1.src | level3 |  |  |
| zerofree-1.1.1-3.oe1.src | level3 |  |  |
| PyPAM-0.5.0-1.oe1.src | level3 |  |  |
| TeXamator-1.7.5-11.oe1.src | level3 |  |  |
| acpica-tools-20190509-4.oe1.src | level3 |  |  |
| adcli-0.8.2-6.oe1.src | level3 |  |  |
| aide-0.16-16.oe1.src | level3 |  |  |
| amanda-3.5.1-18.oe1.src | level3 | amanda-3.5.1-18.oe1.aarch64 |  |
| annobin-8.23-2.oe1.src | level3 |  |  |
| arm-trusted-firmware-1.6-1.oe1.src | level3 |  |  |
| arptables-0.0.4-16.oe1.src | level3 |  |  |
| arpwatch-2.1a15-44.oe1.src | level3 |  |  |
| asciidoc-8.6.10-3.oe1.src | level3 |  |  |
| at-3.1.23-5.oe1.src | level3 |  |  |
| atmel-firmware-1.3-20.oe1.src | level3 |  |  |
| authd-1.4.4-1.oe1.src | level3 |  |  |
| authz-0.1-16.oe1.src | level3 |  |  |
| autoconf213-2.13-42.oe1.src | level3 |  |  |
| b43-openfwwf-5.2-21.oe1.src | level3 |  |  |
| b43-tools-019-3.oe1.src | level3 |  |  |
| bind-dyndb-ldap-11.1-13.oe1.src | level3 |  |  |
| blktrace-1.2.0-13.oe1.src | level3 |  |  |
| busybox-1.31.1-6.oe1.src | level3 |  |  |
| byacc-1.9.20170709-9.oe1.src | level3 |  |  |
| cachefilesd-0.10.10-6.oe1.src | level3 |  |  |
| cgdcbxd-1.0.2-9.oe1.src | level3 |  |  |
| chrpath-0.16-11.oe1.src | level3 |  |  |
| cloud-init-17.1-12.oe1.src | level3 |  |  |
| compiler-rt-7.0.0-2.oe1.src | level3 |  |  |
| comps-extras-24-5.oe1.src | level3 |  |  |
| conntrack-tools-1.4.4-9.oe1.src | level3 |  |  |
| console-setup-1.184-5.oe1.src | level3 |  |  |
| containerd-1.2.0-101.oe1.src | level3 |  |  |
| convmv-2.05-5.oe1.src | level3 |  |  |
| cppcheck-1.83-5.oe1.src | level3 |  |  |
| crash-gcore-command-1.3.1-1.oe1.src | level3 |  |  |
| crash-trace-command-2.0-15.oe1.src | level3 |  |  |
| cscope-15.9-3.oe1.src | level3 |  |  |
| cvs-1.11.23-53.oe1.src | level3 |  |  |
| dblatex-0.3.10-8.oe1.src | level3 |  |  |
| dconf-editor-3.30.2-2.oe1.src | level3 | dconf-editor-3.30.2-2.oe1.aarch64 |  |
| diffstat-1.62-3.oe1.src | level3 |  |  |
| docbook2X-0.8.8-32.oe1.src | level3 |  |  |
| docbook5-style-xsl-1.79.2-8.oe1.src | level3 |  |  |
| docker-anaconda-addon-0.4-10.oe1.src | level3 |  |  |
| docker-compose-1.22.0-2.oe1.src | level3 |  |  |
| docker-proxy-0.8.0.dev.2-100.oe1.src | level3 |  |  |
| dos2unix-7.4.0-11.oe1.src | level3 |  |  |
| dropwatch-1.5-7.oe1.src | level3 |  |  |
| dump-0.4-2.oe1.src | level3 |  |  |
| dwz-0.12-11.oe1.src | level3 |  |  |
| ed-1.14.2-6.oe1.src | level3 |  |  |
| edk2-201908-9.oe1.src | level3 |  |  |
| efibootmgr-16-5.oe1.src | level3 |  |  |
| enscript-1.6.6-19.oe1.src | level3 |  |  |
| esc-1.1.2-4.oe1.src | level3 |  |  |
| fakechroot-2.19-8.oe1.src | level3 | fakechroot-2.19-8.oe1.aarch64 |  |
| fcoe-utils-1.0.32-7.oe1.src | level3 |  |  |
| fetchmail-6.3.26-23.oe1.src | level3 |  |  |
| fio-3.7-4.oe1.src | level3 |  |  |
| firefox-62.0.3-4.oe1.src | level3 | firefox-62.0.3-4.oe1.aarch64 |  |
| fpaste-0.4.0.0-1.oe1.src | level3 |  |  |
| ftp-0.17-79.oe1.src | level3 |  |  |
| gcc_secure-1.0-0.6.oe1.src | level3 |  |  |
| geolite2-20181002-2.oe1.src | level3 |  |  |
| gnome-calculator-3.30.1-2.oe1.src | level3 | gnome-calculator-3.30.1-2.oe1.aarch64 |  |
| gnome-clocks-3.30.0-4.oe1.src | level3 | gnome-clocks-3.30.0-4.oe1.aarch64 |  |
| gnome-common-3.18.0-7.oe1.src | level3 |  |  |
| gnome-dictionary-3.26.1-4.oe1.src | level3 | gnome-dictionary-3.26.1-4.oe1.aarch64 |  |
| gnome-disk-utility-3.30.2-2.oe1.src | level3 | gnome-disk-utility-3.30.2-2.oe1.aarch64 |  |
| gnome-font-viewer-3.30.0-2.oe1.src | level3 | gnome-font-viewer-3.30.0-2.oe1.aarch64 |  |
| gnome-initial-setup-3.30.0-3.oe1.src | level3 |  |  |
| gnome-screenshot-3.30.0-2.oe1.src | level3 |  |  |
| gnome-shell-extensions-3.30.1-2.oe1.src | level3 |  |  |
| gnome-system-monitor-3.28.2-2.oe1.src | level3 | gnome-system-monitor-3.28.2-2.oe1.aarch64 |  |
| gnome-terminal-3.30.1-3.oe1.src | level3 | gnome-terminal-3.30.1-3.oe1.aarch64 |  |
| gnuplot-5.0.6-12.oe1.src | level3 |  |  |
| go-compilers-1-33.oe1.src | level3 |  |  |
| golang-github-cpuguy83-go-md2man-1.0.7-9.20191205git1d903dc.oe1.src | level3 |  |  |
| golang-github-d2g-dhcp4-0-0.11.20191216gita1d1b6c.oe1.src | level3 |  |  |
| golang-googlecode-goprotobuf-0-0.38.20200311git5afd06f.oe1.src | level3 |  |  |
| gphoto2-2.5.17-3.oe1.src | level3 |  |  |
| gubbi-fonts-1.3-4.oe1.src | level3 |  |  |
| xvattr-1.3-40.oe1.src | level3 |  |  |
| haproxy-1.8.14-4.oe1.src | level3 |  |  |
| hardlink-1.0-24.oe1.src | level3 |  |  |
| help2man-1.47.11-0.oe1.src | level3 |  |  |
| hplip-3.18.6-9.oe1.src | level3 | hplip-3.18.6-9.oe1.aarch64 |  |
| hunspell-de-0.20161207-3.oe1.src | level3 |  |  |
| ibus-hangul-1.5.1-3.oe1.src | level3 |  |  |
| ibus-libpinyin-1.10.0-5.oe1.src | level3 | ibus-libpinyin-1.10.0-5.oe1.aarch64 |  |
| ibus-libzhuyin-1.8.92-4.oe1.src | level3 | ibus-libzhuyin-1.8.92-4.oe1.aarch64 |  |
| ibus-m17n-1.4.1-2.oe1.src | level3 | ibus-m17n-1.4.1-2.oe1.aarch64 |  |
| ibus-sayura-1.3.2-15.oe1.src | level3 | ibus-sayura-1.3.2-15.oe1.aarch64 |  |
| ibus-table-array30-1.2.0.20090729-7.oe1.src | level3 |  |  |
| ibus-typing-booster-2.1.2-3.oe1.src | level3 | ibus-typing-booster-2.1.2-3.oe1.noarch |  |
| icfg-0.9-18.oe1.src | level3 |  |  |
| imake-1.0.7-16.oe1.src | level3 |  |  |
| initial-setup-0.3.62-5.oe1.src | level3 |  |  |
| intltool-0.51.0-14.oe1.src | level3 |  |  |
| iotop-0.6-20.oe1.src | level3 |  |  |
| ipmitool-1.8.18-14.oe1.src | level3 |  |  |
| iprutils-2.4.16.1-5.oe1.src | level3 |  |  |
| iptraf-ng-1.1.4-21.oe1.src | level3 |  |  |
| iptstate-2.2.6-9.oe1.src | level3 |  |  |
| ivtv-firmware-20080701-36.oe1.src | level3 |  |  |
| kata-containers-v1.7.0-18.oe1.src | level3 |  |  |
| kdump-anaconda-addon-005-2.oe1.src | level3 |  |  |
| keepalived-2.0.12-2.oe1.src | level3 |  |  |
| keyrings-filesystem-1-12.oe1.src | level3 |  |  |
| kpatch-2.0-3.1.26.oe1.src | level3 |  |  |
| ksh-2020.0.0-2.oe1.src | level3 |  |  |
| kurdit-unikurd-web-fonts-20020502-23.oe1.src | level3 |  |  |
| langpacks-1.0-16.oe1.src | level3 |  |  |
| latex2html-2018.3-3.oe1.src | level3 |  |  |
| latrace-0.5.11-13.oe1.src | level3 | latrace-0.5.11-13.oe1.aarch64 |  |
| sblim-cmpi-devel-2.0.3-19.oe1.src | level3 | libcmpiCppImpl0-2.0.3-19.oe1.aarch64 |  |
| liberation-fonts-2.00.3-2.oe1.src | level3 |  |  |
| libkae-1.2.10-3.oe1.src | level3 | libkae-1.2.10-3.oe1.aarch64 |  |
| linuxdoc-tools-0.9.72-7.oe1.src | level3 |  |  |
| linuxptp-2.0-3.oe1.src | level3 |  |  |
| lklug-fonts-0.6-19.20090803cvs.oe1.src | level3 |  |  |
| lshw-B.02.18-21.oe1.src | level3 | lshw-B.02.18-21.oe1.aarch64 |  |
| ltrace-0.7.91-29.oe1.src | level3 |  |  |
| lua-filesystem-1.6.3-10.oe1.src | level3 |  |  |
| lua-lunit-0.5-15.oe1.src | level3 |  |  |
| lxcfs-3.0.2-0.5.h2.oe1.src | level3 | lxcfs-3.0.2-0.5.h2.oe1.aarch64 |  |
| lxcfs-tools-0.3-16.oe1.src | level3 |  |  |
| madan-fonts-2.000-24.oe1.src | level3 |  |  |
| mailman-2.1.29-5.oe1.src | level3 |  |  |
| man-pages-5.02-4.oe1.src | level3 |  |  |
| mc-4.8.21-3.oe1.src | level3 |  |  |
| mercurial-5.1-2.oe1.src | level3 |  |  |
| meson-0.51.1-3.oe1.src | level3 |  |  |
| mikmod-3.2.8-6.oe1.src | level3 |  |  |
| minicom-2.7.1-11.oe1.src | level3 |  |  |
| mksh-56c-5.oe1.src | level3 |  |  |
| mod_fcgid-2.3.9-18.oe1.src | level3 |  |  |
| mod_security-2.9.2-7.oe1.src | level3 |  |  |
| mrtg-2.17.7-3.oe1.src | level3 |  |  |
| mstflint-4.10.0-4.oe1.src | level3 |  |  |
| mtr-0.92-8.oe1.src | level3 |  |  |
| multilib-rpm-config-1-14.oe1.src | level3 |  |  |
| mutt-1.10.1-2.oe1.src | level3 |  |  |
| mypaint-brushes-1.3.0-3.oe1.src | level3 |  |  |
| mysql-8.0.17-3.oe1.src | level3 | mysql-8.0.17-3.oe1.aarch64 |  |
| nafees-web-naskh-fonts-1.2-21.oe1.src | level3 |  |  |
| nano-4.5-2.oe1.src | level3 |  |  |
| nasm-2.13.03-4.oe1.src | level3 |  |  |
| navilu-fonts-1.2-14.oe1.src | level3 |  |  |
| ncompress-4.2.4.4-18.oe1.src | level3 |  |  |
| ndisc6-1.0.4-1.oe1.src | level3 |  |  |
| netlabel_tools-0.30.0-6.oe1.src | level3 |  |  |
| nfs4-acl-tools-0.3.4-5.oe1.src | level3 |  |  |
| nss-altfiles-2.18.1-8.oe1.src | level3 | nss-altfiles-2.18.1-8.oe1.aarch64 |  |
| nss-mdns-0.14.1-3.oe1.src | level3 | nss-mdns-0.14.1-3.oe1.aarch64 |  |
| nss-pam-ldapd-0.9.9-5.oe1.src | level3 | nss-pam-ldapd-0.9.9-5.oe1.aarch64 |  |
| nss-pem-1.0.4-2.oe1.src | level3 | nss-pem-1.0.4-2.oe1.aarch64 |  |
| nss_wrapper-1.1.3-2.oe1.src | level3 | nss_wrapper-1.1.3-2.oe1.aarch64 |  |
| ntpstat-0.5-3.oe1.src | level3 |  |  |
| nvme-cli-1.6-2.oe1.src | level3 |  |  |
| nvmetcli-0.4-6.oe1.src | level3 |  |  |
| obs-env-1.0-5.oe1.src | level3 |  |  |
| obs-service-download_files-0.6.2-0.oe1.src | level3 |  |  |
| obs-service-extract_file-0.3-4.oe1.src | level3 |  |  |
| obs-service-rust2rpm-1-3.oe1.src | level3 |  |  |
| obs-service-set_version-0.5.10-5.oe1.src | level3 |  |  |
| oci-systemd-hook-0.1.17-4.oe1.src | level3 |  |  |
| oddjob-0.34.4-8.oe1.src | level3 |  |  |
| openEuler-latest-release-1.0-158503277620.03.oe1.src | level3 |  |  |
| openvpn-2.4.8-3.oe1.src | level3 |  |  |
| openvswitch-2.12.0-5.oe1.src | level3 | openvswitch-2.12.0-5.oe1.aarch64 |  |
| openvswitch-kmod-2.12.0-1.oe1.src | level3 |  |  |
| orca-3.30.0-2.oe1.src | level3 |  |  |
| pam_krb5-2.4.13-12.oe1.src | level3 |  |  |
| patchutils-0.3.4-13.oe1.src | level3 |  |  |
| perftest-4.2-4.oe1.src | level3 |  |  |
| perl-BSD-Resource-1.291.100-8.oe1.src | level3 |  |  |
| perl-Business-ISBN-3.005-1.oe1.src | level3 |  |  |
| perl-Canary-Stability-2013-4.oe1.src | level3 |  |  |
| perl-Class-XSAccessor-1.19-18.oe1.src | level3 |  |  |
| perl-Config-AutoConf-0.317-4.oe1.src | level3 |  |  |
| perl-Config-IniFiles-2.98-4.oe1.src | level3 |  |  |
| perl-Crypt-OpenSSL-Bignum-0.09-6.oe1.src | level3 |  |  |
| perl-Crypt-PasswdMD5-1.4.0-14.oe1.src | level3 |  |  |
| perl-Devel-CheckLib-1.13-5.oe1.src | level3 |  |  |
| perl-Encode-Detect-1.01-29.oe1.src | level3 |  |  |
| perl-File-Listing-6.04-20.oe1.src | level3 |  |  |
| perl-File-ShareDir-Install-0.13-4.oe1.src | level3 |  |  |
| perl-File-pushd-1.016-5.oe1.src | level3 |  |  |
| perl-Font-TTF-1.06-9.oe1.src | level3 |  |  |
| perl-HTTP-Daemon-6.01-26.oe1.src | level3 |  |  |
| perl-List-MoreUtils-0.428-5.oe1.src | level3 |  |  |
| perl-MIME-Lite-3.030-14.oe1.src | level3 |  |  |
| perl-Module-Build-Tiny-0.039-17.oe1.src | level3 |  |  |
| perl-Module-Install-AuthorTests-0.002-18.oe1.src | level3 |  |  |
| perl-Module-Install-AutoLicense-0.10-7.oe1.src | level3 |  |  |
| perl-Module-Install-Repository-0.06-23.oe1.src | level3 |  |  |
| perl-Module-Package-Au-2-16.oe1.src | level3 |  |  |
| perl-Module-ScanDeps-1.27-6.oe1.src | level3 |  |  |
| perl-Net-CIDR-Lite-0.21-23.oe1.src | level3 |  |  |
| perl-Net-Daemon-0.48-2.oe1.src | level3 |  |  |
| perl-Net-LibIDN-0.12-33.oe1.src | level3 |  |  |
| perl-NetAddr-IP-4.079-10.oe1.src | level3 |  |  |
| perl-Package-Constants-0.06-1.oe1.src | level3 |  |  |
| perl-Path-Class-0.37-14.oe1.src | level3 |  |  |
| perl-Path-Tiny-0.108-2.oe1.src | level3 |  |  |
| perl-Readonly-2.05-8.oe1.src | level3 |  |  |
| perl-String-ShellQuote-1.04-26.oe1.src | level3 |  |  |
| perl-Sub-Name-0.21-10.oe1.src | level3 |  |  |
| perl-Switch-2.17-13.oe1.src | level3 |  |  |
| perl-Sys-CPU-0.61-18.oe1.src | level3 |  |  |
| perl-Sys-MemInfo-0.99-9.oe1.src | level3 |  |  |
| perl-Test-Deep-1.128-4.oe1.src | level3 |  |  |
| perl-Test-FailWarnings-0.008-15.oe1.src | level3 |  |  |
| perl-Test-Fatal-0.014-13.oe1.src | level3 |  |  |
| perl-Test-File-1.44.3-7.oe1.src | level3 |  |  |
| perl-Test-InDistDir-1.112071-10.oe1.src | level3 |  |  |
| perl-Test-LeakTrace-0.16-10.oe1.src | level3 |  |  |
| perl-Test-Needs-0.002005-8.oe1.src | level3 |  |  |
| perl-Test-NoWarnings-1.04-17.oe1.src | level3 |  |  |
| perl-Test-Pod-1.52-4.oe1.src | level3 |  |  |
| perl-Test-Pod-Coverage-1.10-14.oe1.src | level3 |  |  |
| perl-Test-Requires-0.10-15.oe1.src | level3 |  |  |
| perl-Test-RequiresInternet-0.05-12.oe1.src | level3 |  |  |
| perl-Test-Warnings-0.026-11.oe1.src | level3 |  |  |
| perl-Unicode-LineBreak-2019.001-5.oe1.src | level3 |  |  |
| perl-XML-Writer-0.625-14.oe1.src | level3 |  |  |
| perl-generators-1.10-8.oe1.src | level3 |  |  |
| perl-libxml-perl-0.08-36.oe1.src | level3 |  |  |
| perl-strictures-2.000006-7.oe1.src | level3 |  |  |
| pngquant-2.12.5-2.oe1.src | level3 |  |  |
| pnm2ppa-1.04-42.oe1.src | level3 |  |  |
| po4a-0.54-2.oe1.src | level3 |  |  |
| pps-tools-1.0.2-3.oe1.src | level3 |  |  |
| pptp-1.10.0-6.oe1.src | level3 |  |  |
| procinfo-18-42.oe1.src | level3 |  |  |
| procmail-3.22-50.oe1.src | level3 |  |  |
| psacct-6.6.4-4.oe1.src | level3 |  |  |
| pyflakes-2.0.0-8.oe1.src | level3 |  |  |
| pyliblzma-0.5.3-28.oe1.src | level3 |  |  |
| python-ecdsa-0.14.1-1.oe1.src | level3 |  |  |
| python-filelock-3.0.12-1.oe1.src | level3 |  |  |
| python-html5lib-1.0.1-5.oe1.src | level3 |  |  |
| python-hwdata-2.3.7-5.oe1.src | level3 |  |  |
| python-hyperlink-18.0.0-8.oe1.src | level3 |  |  |
| python-hypothesis-3.66.11-2.oe1.src | level3 |  |  |
| python-importlib-metadata-0.23-1.oe1.src | level3 |  |  |
| python-kitchen-1.2.6-1.oe1.src | level3 |  |  |
| python-ldap-3.1.0-1.oe1.src | level3 |  |  |
| python-setuptools_scm-3.1.0-2.oe1.src | level3 |  |  |
| python-toml-0.10.0-1.oe1.src | level3 |  |  |
| python-which-1.1.0-23.oe1.src | level3 |  |  |
| Cython-0.29.14-1.oe1.src | level3 |  |  |
| python-PyMySQL-0.9.2-3.oe1.src | level3 |  |  |
| python-backports-unittest_mock-1.2.1-7.oe1.src | level3 |  |  |
| python-beaker-1.10.0-4.oe1.src | level3 |  |  |
| python-blinker-1.4-4.oe1.src | level3 |  |  |
| python-bottle-0.12.13-7.oe1.src | level3 |  |  |
| python-cheetah-3.1.0-7.oe1.src | level3 |  |  |
| python-constantly-15.1.0-4.oe1.src | level3 |  |  |
| python-cryptography-vectors-2.6.1-1.oe1.src | level3 |  |  |
| python-cycler-0.10.0-2.oe1.src | level3 |  |  |
| dogtail-0.9.10-2.oe1.src | level3 |  |  |
| python-freezegun-0.3.8-12.oe1.src | level3 |  |  |
| future-0.16.0-11.oe1.src | level3 |  |  |
| python-genshi-0.7-23.oe1.src | level3 |  |  |
| python-google-apputils-0.4.2-15.oe1.src | level3 |  |  |
| python-hamcrest-1.9.0-8.oe1.src | level3 |  |  |
| python-httplib2-0.13.1-4.oe1.src | level3 |  |  |
| python-httpretty-0.9.5-4.oe1.src | level3 |  |  |
| python-incremental-17.5.0-5.oe1.src | level3 |  |  |
| python-keyczar-0.71c-13.oe1.src | level3 |  |  |
| python-keyring-13.2.1-4.oe1.src | level3 |  |  |
| python-kiwisolver-1.1.0-3.oe1.src | level3 |  |  |
| python-markdown-2.4.1-13.oe1.src | level3 |  |  |
| python-memcached-1.58-1.oe1.src | level3 |  |  |
| python-mox-0.5.3-18.oe1.src | level3 |  |  |
| python-netaddr-0.7.19-14.oe1.src | level3 |  |  |
| python-parse-1.8.4-2.oe1.src | level3 |  |  |
| python-pocketlint-0.17-2.oe1.src | level3 |  |  |
| python-pretend-1.0.8-15.oe1.src | level3 |  |  |
| pyelftools-0.25-2.oe1.src | level3 |  |  |
| python-pytest-cov-2.5.1-7.oe1.src | level3 |  |  |
| python-pytest-mock-1.10.0-4.oe1.src | level3 |  |  |
| python-pytest-virtualenv-1.2.11-11.oe1.src | level3 |  |  |
| python-redis-2.10.6-6.oe1.src | level3 |  |  |
| python-rsa-3.4.2-11.oe1.src | level3 |  |  |
| scons-3.0.1-10.oe1.src | level3 |  |  |
| python-setuptools_git-1.1-10.oe1.src | level3 |  |  |
| python-sphinxcontrib-spelling-4.2.0-2.oe1.src | level3 |  |  |
| python-subprocess32-3.2.7-1.oe1.src | level3 |  |  |
| python-suds-0.7-2.oe1.src | level3 |  |  |
| python-sure-1.4.11-4.oe1.src | level3 |  |  |
| python-testscenarios-0.5.0-14.oe1.src | level3 |  |  |
| python-tornado-5.0.2-5.oe1.src | level3 |  |  |
| python-zope-interface-4.5.0-3.oe1.src | level3 |  |  |
| python-flit-1.0-5.oe1.src | level3 |  |  |
| python3-mallard-ducktype-0.3-5.oe1.src | level3 |  |  |
| python-pytest-expect-1.1.0-3.oe1.src | level3 |  |  |
| python-threadpoolctl-1.1.0-2.oe1.src | level3 |  |  |
| qperf-0.4.9-15.oe1.src | level3 |  |  |
| qt5-doc-5.11.1-4.oe1.src | level3 |  |  |
| qt5-qttranslations-5.11.1-4.oe1.src | level3 |  |  |
| rasdaemon-0.6.3-2.oe1.src | level3 |  |  |
| rdate-1.5-0.oe1.src | level3 |  |  |
| redhat-menus-12.0.2-14.h3.oe1.oe1.src | level3 |  |  |
| redis-4.0.11-5.oe1.src | level3 |  |  |
| rng-tools-6.3.1-4.oe1.src | level3 |  |  |
| rootfiles-8.1-25.oe1.src | level3 |  |  |
| rootsh-1.5.3-15.oe1.src | level3 |  |  |
| rpcsvc-proto-1.4-2.oe1.src | level3 |  |  |
| rpmlint-1.10-18.oe1.src | level3 |  |  |
| rpmrebuild-2.11-7.oe1.src | level3 |  |  |
| ruby-common-2.1-106.3.oe1.src | level3 |  |  |
| saab-fonts-0.91-19.oe1.src | level3 |  |  |
| sane-frontends-1.0.14-32.oe1.src | level3 |  |  |
| sassc-3.5.0-2.oe1.src | level3 |  |  |
| sblim-sfcb-1.4.9-14.oe1.src | level3 | sblim-sfcb-1.4.9-14.oe1.aarch64 |  |
| scap-workbench-1.2.0-2.oe1.src | level3 | scap-workbench-1.2.0-2.oe1.aarch64 |  |
| sdparm-1.10-9.oe1.src | level3 |  |  |
| security-tool-2.0-1.43.oe1.src | level3 |  |  |
| setserial-2.17-50.oe1.src | level3 |  |  |
| shim-15-18.oe1.src | level3 |  |  |
| shim-unsigned-aarch64-15-2.oe1.src | level3 |  |  |
| sil-nuosu-fonts-2.1.1-17.oe1.src | level3 |  |  |
| sil-padauk-fonts-3.003-4.oe1.src | level3 |  |  |
| smartmontools-6.6-8.oe1.src | level3 |  |  |
| smc-fonts-6.1-12.oe1.src | level3 |  |  |
| socket_wrapper-1.1.9-4.oe1.src | level3 | socket_wrapper-1.1.9-4.oe1.aarch64 |  |
| spice-vdagent-0.18.0-4.oe1.src | level3 |  |  |
| squid-4.9-1.oe1.src | level3 |  |  |
| strongswan-5.7.2-4.oe1.src | level3 | strongswan-5.7.2-4.oe1.aarch64 |  |
| swig-3.0.12-22.oe1.src | level3 |  |  |
| symlinks-1.4-23.oe1.src | level3 |  |  |
| syscontainer-tools-v0.9-36.oe1.src | level3 |  |  |
| system-config-printer-1.5.11-14.oe1.src | level3 | system-config-printer-1.5.11-14.oe1.aarch64 |  |
| tang-7-2.oe1.src | level3 |  |  |
| targetcli-2.1.fb48-9.oe1.src | level3 |  |  |
| tcllib-1.19-2.oe1.src | level3 |  |  |
| tcpdump-4.9.3-3.oe1.src | level3 |  |  |
| tcsh-6.21.00-5.oe1.src | level3 |  |  |
| telnet-0.17-75.oe1.src | level3 |  |  |
| texi2html-5.0-13.oe1.src | level3 |  |  |
| tftp-5.2-27.oe1.src | level3 |  |  |
| time-1.9-7.oe1.src | level3 |  |  |
| tipcutils-2.2.0-3.oe1.src | level3 |  |  |
| tpm-quote-tools-1.0.4-3.oe1.src | level3 |  |  |
| tpm2-abrmd-selinux-2.0.0-3.oe1.src | level3 |  |  |
| traceroute-2.1.0-10.oe1.src | level3 |  |  |
| transfig-3.2.6a-6.oe1.src | level3 |  |  |
| tree-1.7.0-18.oe1.src | level3 |  |  |
| ttmkfdir-3.0.9-56.oe1.src | level3 |  |  |
| uid_wrapper-1.2.4-4.oe1.src | level3 | uid_wrapper-1.2.4-4.oe1.aarch64 |  |
| umoci-0.4.5-2.oe1.src | level3 |  |  |
| uname-build-checks-1.0-0.4.oe1.src | level3 |  |  |
| unicode-emoji-11.0-3.oe1.src | level3 |  |  |
| units-2.17-8.oe1.src | level3 |  |  |
| usbmuxd-1.1.0-16.oe1.src | level3 |  |  |
| usbutils-010-4.oe1.src | level3 |  |  |
| uthash-2.0.2-7.oe1.src | level3 | uthash-2.0.2-7.oe1.aarch64 |  |
| vboot-utils-20180531-3.oe1.src | level3 |  |  |
| vconfig-1.9-26.oe1.src | level3 |  |  |
| vinagre-3.22.0-12.oe1.src | level3 | vinagre-3.22.0-12.oe1.aarch64 |  |
| vino-3.22.0-12.oe1.src | level3 |  |  |
| virt-viewer-7.0-2.oe1.src | level3 | virt-viewer-7.0-2.oe1.aarch64 |  |
| vsftpd-3.0.3-30.oe1.src | level3 |  |  |
| wsmancli-2.6.0-9.oe1.src | level3 |  |  |
| x3270-3.6ga5-4.oe1.src | level3 |  |  |
| xcb-proto-1.13-6.oe1.src | level3 |  |  |
| xdelta-3.1.0-7.oe1.src | level3 |  |  |
| xdg-user-dirs-gtk-0.10-16.oe1.src | level3 |  |  |
| xfsdump-3.1.8-5.oe1.src | level3 |  |  |
| xhtml1-dtds-1.0-20020801.15.oe1.src | level3 |  |  |
| xmlto-0.0.28-15.oe1.src | level3 |  |  |
| xmltoman-0.4-19.oe1.src | level3 |  |  |
| xorg-x11-apps-7.7-23.oe1.src | level3 |  |  |
| xorg-x11-drivers-7.7-28.oe1.src | level3 |  |  |
| xorg-x11-drv-qxl-0.1.5-12.oe1.src | level3 |  |  |
| xorg-x11-utils-7.5-30.oe1.src | level3 |  |  |
| xorg-x11-xtrans-devel-1.3.5-8.oe1.src | level3 |  |  |
| xrestop-0.4-23.oe1.src | level3 |  |  |
| xterm-334-4.oe1.src | level3 | xterm-334-4.oe1.aarch64 |  |
| ypserv-4.1-4.oe1.src | level3 |  |  |
| yum-metadata-parser-1.1.4-24.oe1.src | level3 |  |  |
| zd1211-firmware-1.5-5.oe1.src | level3 |  |  |
| zopfli-1.0.1-8.oe1.src | level3 |  |  |
| zsh-5.7.1-4.oe1.src | level3 |  |  |

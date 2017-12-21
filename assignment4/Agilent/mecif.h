#ifndef MECIF
#define MECIF
/***********************************************************************
*
* $Author: stka $
* $Date: 2000/05/26 13:50:05 $
* $Revision: 2.0 $
* $Source: /mnt5/users/stka/mecif_lib/sources/RCS/mecif.h,v $
*
************************************************************************
*
* CONTENTS : mecif.h - this file contains the common MECIF structures 
*                      and defines
*
************************************************************************


                (C) Copyright Agilent Technologies, Inc. 2000

                            All rights reserved


    Agilent Technologies makes no warranty of any kind with regard to
    this software, including, but not limited to, the implied warranties
    of merchantability and fitness for a particular purpose.  Agilent
    Technologies shall not be liable for errors contained herein or
    direct, indirect, special, incidental or consequential damages in
    connection with the furnishing, performance or use of this material.

*/



/*-INCLUDES-----------------------------------------------------------*/

#include <pc_mach.h>


/*-DEFINES------------------------------------------------------------*/

/* define Par Server source id */
#define PAR_SERVER              32865U

/* definition of the compatibility of the library with the Par Server */
#define PAR_COMPAT_HIGH         3
#define PAR_COMPAT_LOW          0


#define SYNC_HD                 0x1b

#define DIR_SERVER              2
#define RAW_SERVER              3
#define ASCII_ID_LENGTH         8     /* length of ascii id in ListInfo */

/* valid Computer <-> Agilent CMS commands */
#define CONNECT_REQ             1
#define CONNECT_RSP             2
#define TICK_REQ                3
#define TICK_RSP                4
#define OVERFLOW_NOTIFICATION   5
#define ABORT_NOTIFICATION      6
#define DISCONNECT_REQ          7
#define DISCONNECT_RSP          8
#define DIRECTORY_REQ           9
#define DIRECTORY_RSP           10
#define PAR_LIST_REQ            11
#define PAR_LIST_RSP            12
#define TUNE_REQ                13
#define TUNE_RSP                14
#define SINGLE_TUNE_REQ         15
#define SINGLE_TUNE_RSP         16
#define DETUNE_REQ              17
#define DETUNE_RSP              18
#define DETUNE_ALL_REQ          19
#define DETUNE_ALL_RSP          20
#define QUERY_REQ               21
#define QUERY_RSP               22
#define QUERY_LIST_REQ          23
#define QUERY_LIST_RSP          24
#define MIRROR_REQ              25
#define MIRROR_RSP              26
#define MISC_LIST_REQ           27
#define MISC_LIST_RSP           28
#define RAW_ENABLE_REQ          29
#define RAW_ENABLE_RSP          30
#define TUNE_TYPE_REQ           31
#define TUNE_TYPE_RSP           32
#define SINGLE_TUNE_TYPE_REQ    33
#define SINGLE_TUNE_TYPE_RSP    34


#define MIN_MSG_LENGTH          8       /* min. transport length allowed */

#define MAX_MSG_LENGTH          518     /* max. transport length allowed */

#define MAX_TUNE_REQ            245     /* max. # of tune data in words
                                           within one (SINGLE)TUNE_REQ */
#define MAX_TUNE_REQ_IDS        (MAX_TUNE_REQ/7)
                                        /* max. # of MsgId's to be tuned
                                           within one (SINGLE)TUNE_REQ */
#define TICK_NUM                2       /* max. # of TICK data words */
#define MIRROR_NUM              16      /* max. # of MIRROR data words */
#define QUERY_NUM               245     /* max. # of QUERY cmd words */
#define MAX_SERVICES            8       /* max. # of service descriptions
                                           in one DIRECTORY_RSP */
#define MAX_QUERY_APP           242     /* max. # of QUERY_REQ appended
                                           words */

#define MAX_TUNE_TYPE_WORDS     238     /* max. # of (SINGLE_)TUNE_TYPE_REQ
                                           appended words */

#define MAX_LIST_IDS            19      /* max. # of message ID's */
#define MAX_QUERY_IDS           247     /* max. # of query ID's */
#define MAX_DETUNE_REQ          122     /* max. # of MPB headers to be
                                         detuned within one DETUNE_REQ/RSP */
#define MAX_MPB_DATA            254   /* max. # of data words in TUNE_DATA */
#define DESCR_CHAR              26      /* max. # of words ascii MECIF
                                           serverdescr. */



/* transport header at the beginning of each Computer <-> Agilent CMS message */
typedef struct
        {
        u_16            trans_len;
        u_16            dest_id;
        u_16            src_id;
        } TransHd;


/* definition of a Agilent CMS SPI MsgId */
typedef struct
        {
        u_16            SourceId;
        u_16            ChannelId;
        u_16            MsgType;
        u_8             ChannelNo;
        u_8             SourceNo;
        u_8             Unused;
        u_8             Layer;
        } MsgIdTyp;

/* definition of DirInfo in DIRECTORY_RSP */
typedef struct
        {
        u_16            serv_id;
        u_16            num_connections;
        c_16            serv_descr[DESCR_CHAR];
        u_8             compat_low;
        u_8             compat_high;
        } DirInfo;


/* definition of ListInfo in PAR_LIST_RSP */
typedef struct {
        MsgIdTyp        msg_id_typ;
        u_16            ascii_id[ASCII_ID_LENGTH];
        } ListInfo;


/* definition of DetuneInfo in DETUNE_REQ/RSP */
typedef struct
        {
        u_16            mpb_hd;
        u_16            rsp;
        } DetuneInfo;


/****************  definition of MECIF commands   *********************/

/* CONNECT_REQ message from Computer to Agilent CMS */
typedef struct
        {
        TransHd         trans_hd;
        u_16            cmd;
        u_16            tick_period;
        } ConReq;

/* DISCONNECT_REQ message from Computer to Agilent CMS */
/* DIRECTORY_REQ message from Computer to Agilent CMS */
/* PAR_LIST_REQ message from Computer to Agilent CMS */
/* ABORT_NOTIFICATION message from Agilent CMS to Computer */
/* QUERY_LIST_REQ message from Computer to Agilent CMS */
/* MISC_LIST_REQ message from Computer to Agilent CMS */
typedef struct
        {
        TransHd         trans_hd;
        u_16            cmd;
        } DisconReq;

typedef DisconReq       DirReq;
typedef DisconReq       ListReq;
typedef DisconReq       AbortNot;
typedef DisconReq       QListReq;
typedef DisconReq       MListReq;


/* CONNECT_RSP message from Agilent CMS to Computer */
typedef struct
        {
        TransHd         trans_hd;
        u_16            cmd;
        u_16            window;
        u_8             compat_low;
        u_8             compat_high;
        u_8             ret;
        u_8             error;
        } ConRsp;


/* TICK_REQ message from Computer to Agilent CMS */
/* TICK_RSP message from Agilent CMS to Computer */
typedef struct
        {
        TransHd         trans_hd;
        u_16            cmd;
        u_16            tick_info[TICK_NUM];
        } TickReq;

typedef TickReq         TickRsp;


/* MIRROR_REQ message from Computer to Agilent CMS */
/* MIRROR_RSP message from Agilent CMS to Computer */
typedef struct
        {
        TransHd         trans_hd;
        u_16            cmd;
        u_16            mirror_info[MIRROR_NUM];
        } MirrorReq;

typedef MirrorReq       MirrorRsp;


/* OVERFLOW_NOTIFICATION message from Agilent CMS to Computer */
typedef struct
        {
        TransHd         trans_hd;
        u_16            cmd;
        u_16            error_code;
        } OvflowNot;


/* DISCONNECT_RSP message from Agilent CMS to Computer */
typedef struct
        {
        TransHd         trans_hd;
        u_16            cmd;
        u_16            rsp;
        } DisconRsp;


/* DIRECTORY_RSP message from Agilent CMS to Computer */
typedef struct
        {
        TransHd         trans_hd;
        u_16            cmd;
        u_8             total;
        u_8             actual;
        u_16            language;
        DirInfo         dir_info[MAX_SERVICES];
        } DirRsp;


/* PAR_LIST_RSP message from Agilent CMS to Compter */
/* MISC_LIST_RSP message from Agilent CMS to Compter */
typedef struct
        {
        TransHd         trans_hd;
        u_16            cmd;
        u_8             total;
        u_8             actual;
        ListInfo        list_info[MAX_LIST_IDS];
        } ListRsp;

typedef ListRsp         MListRsp;


/* (SINGLE)TUNE_REQ message from Computer to Agilent CMS */
/* (SINGLE)TUNE_RSP message from Agilent CMS to Computer */
typedef struct
        {
        TransHd         trans_hd;
        u_16            cmd;
        u_16            tune_id;
        u_16            tune_info[MAX_TUNE_REQ];
        } TuneReq;

typedef TuneReq         TuneRsp;


/* (SINGLE_)TUNE_TYPE_REQ message from Computer to Agilent CMS */
/* (SINGLE_)TUNE_TYPE_RSP message from Agilent CMS to Computer */
typedef struct
        {
        TransHd         trans_hd;
        u_16            cmd;
        u_16            tune_id;
        u_16            msg_type;
        u_8             rec_id;
        u_8             app_rec_len;
        u_16            tune_info[MAX_TUNE_TYPE_WORDS];
        } TuneTypeReq;

typedef struct
        {
        TransHd         trans_hd;
        u_16            cmd;
        u_16            tune_id;
        u_16            msg_type;
        u_8             total;
        u_8             actual;
        u_8             rec_id;
        u_8             app_rec_len;
        u_16            tune_info[MAX_TUNE_REQ - 1];
        } TuneTypeRsp;


/* TUNE_DATA message from Agilent CMS to Computer */
typedef struct
        {
        TransHd         trans_hd;
        u_16            mpb_hd;
        u_16            mpb_len;
        u_16            mpb_data[MAX_MPB_DATA];
        } TuneData;


/* DETUNE_REQ message from Computer to Agilent CMS */
/* DETUNE_RSP message from Agilent CMS to Computer */
typedef struct
        {
        TransHd         trans_hd;
        u_16            cmd;
        u_16            tune_id;
        DetuneInfo      detune_info[MAX_DETUNE_REQ];
        } DetuneReq;

typedef DetuneReq               DetuneRsp;


/* DETUNE_ALL_REQ message from Computer to Agilent CMS */
typedef struct
        {
        TransHd         trans_hd;
        u_16            cmd;
        u_16            tune_id;
        u_16            msg_type;
        } DetAllReq;


/* DETUNE_ALL_RSP message from Agilent CMS to Computer */
typedef struct
        {
        TransHd         trans_hd;
        u_16            cmd;
        u_16            tune_id;
        u_16            rsp;
        u_16            msg_type;
        } DetAllRsp;


/* QUERY_REQ message from Computer to Agilent CMS */
/* QUERY_RSP message from Agilent CMS to Computer  */
typedef struct {
        TransHd         trans_hd;
        u_16            cmd;
        u_16            query_id;
        u_16            tune_id;
        u_8             rsp;
        u_8             app_rec_len;
        b_16            query_cmd[QUERY_NUM];
        } QueryReq;

typedef QueryReq        QueryRsp;


/* QUERY_LIST_RSP message from Agilent CMS to Compter */
typedef struct
        {
        TransHd         trans_hd;
        u_16            cmd;
        u_8             total;
        u_8             actual;
        u_16            query_id[MAX_QUERY_IDS];
        } QListRsp;


/* RAW_ENABLE_REQ message from Computer to Agilent CMS */
typedef struct {
        TransHd         trans_hd;
        u_16            cmd;
        u_16            password1;
        u_16            password2;
        } RawEnReq;

/* RAW_ENABLE_RSP message from Agilent CMS to Computer  */
typedef struct {
        TransHd         trans_hd;
        u_16            cmd;
        u_16            rsp;
        } RawEnRsp;

/*Paulo: Acrescentado: */
typedef struct{
       TransHd          trans_hd;
       u_16             cmd;
       } CMDHeader;
#endif

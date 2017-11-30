#ifndef PC_ALMGR
#define PC_ALMGR
/***********************************************************************
*
* $Author: stka $
* $Date: 2000/05/26 13:47:37 $
* $Revision: 2.0 $
* $Source: /mnt5/users/stka/mecif_lib/sources/RCS/pc_almgr.h,v $
*
************************************************************************
*
* CONTENTS: pc_almgr.h - contains typedefs and defines of CMS 
*                        alarm information
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



/*-SYSTEM INCLUDES----------------------------------------------------*/

/* it is assumed that pc_mach.h and pc_spi.h was included previously */


/*-MODULE INCLUDES----------------------------------------------------*/


/*-DEFINES------------------------------------------------------------*/

/* defines for the Bed Label length
  ---------------------------------                                   */

#define         BEDLABEL        6               /* used by the SDN    */
#define         BEDLABEL_TL     BEDLABEL +1     /* include EOS!       */

/* Defines for the Patient Name and Patient_Id message
  ---------------------------------------------------                 */

#define         PAT_NAME        18              /* used by the SDN    */
#define         PAT_NAME_TL     PAT_NAME+1      /* include EOS!       */

#define         PAT_ID          12              /* used by the SDN    */
#define         PAT_ID_TL       PAT_ID+1        /* include EOS!       */


/*-TYPEDEFS-----------------------------------------------------------*/

/* defines for the Bit definitions in the AlMngrAs message.
   --------------------------------------------------------           */

/* al_status1 Bit definitions - see AlMngrAs structure.
   ----------------------------------------------------               */

/* Bits 5-6 define the alarm text severity - Set by the Alarm Mgr.    */
#define AL_STAT1_ALARM_MASK             0x60    /* alarm text mask    */
#define AL_STAT1_RED                    0x40    /* Red alarm          */
#define AL_STAT1_YELLOW                 0x20    /* yellow alarm       */

/* Bits 3-4 define the inop text severity  - Set by the Alarm Mgr.    */
#define AL_STAT1_INOP_MASK              0x18    /* inop text mask     */
#define AL_STAT1_HARD                   0x10    /* hard inop          */
#define AL_STAT1_SOFT                   0x08    /* soft inop          */

/* Bed Suspend Status. Is set by AlarmMgr.                            */
#define AL_STAT3_MAIN_AL_OFF            0x04    /* =1 if Al Suspended */

/* Monitor Status. Is set by the AlarmMgr                             */
#define UNIT_CAP_MONIT_STATUS   0x400           /* =1 if Standby      */


/* AlertStatus message as sent out by the Alarm Mgr to SDN            */

typedef struct {
            b_16   mpb_header;
            u_8    mpb_length;
            u_8    rec_size;
            b_16   al_status1;  /* first word of Alert Status Msg     */
            b_16   al_status3;  /* second word of Alert Status Msg    */
            b_16   unit_cap;    /* third word - NEW                   */
               } AlMngrAs;


/* Alarm Text Message (AT) as sent out by the Alarm Mgr to the SDN    */

typedef struct {
            b_16   mpb_header;
            b_16   mpb_length;
            b_16   psw;         /* psw from SpiAL message.            */
            b_16   flags1;      /* flags from SpiAlFixed - alrec inh  */
            b_16   source_id;   /* alert source_id/no                 */
            b_16   source_num;
            b_16   label_code;  /* label_code as from SpiAL           */
            b_16   flags2;      /* flags from SpiAlert                */
            b_16   alert_code;  /* alert code/string from SpiAl       */
            c_16   alert_string[SPI_ALERT_LENGTH];
               } AlMngrAl;

/* INop Text message as sent out by the Alarm Mgr and received by SDN */

typedef struct {
            b_16   mpb_header;
            b_16   mpb_length;
            b_16   psw;
            b_16   source_id;
            b_16   source_num;
            b_16   label_code;
            b_16   flags2;      /* flags from SpiAlert                */
            b_16   alert_code;
            c_16   alert_string[SPI_ALERT_LENGTH];
               } AlMngrIn;



/* Typedef for the Pat_name/Id message                                */

typedef struct
     {
     u_16       header;
     u_8        msg_length;
     u_8        unused_byte;
     b_16       change_ct;
     c_16       pat_name[PAT_NAME_TL];
     c_16       pat_id[PAT_ID_TL];
     } MpbPni;



/* typedef for the Bed label message                                  */

typedef struct
    {
    u_16        header;
    u_8         msg_length;
    u_8         unused_byte;
    c_16        label[BEDLABEL_TL];
    }  MpbBedlb;


#endif

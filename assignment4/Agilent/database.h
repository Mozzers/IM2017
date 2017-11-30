#ifndef DATABASE
#define DATABASE
/***********************************************************************
*
* $Author: stka $
* $Date: 2000/05/26 13:48:57 $
* $Revision: 2.0 $
* $Source: /mnt5/users/stka/mecif_lib/sources/RCS/database.h,v $
*
************************************************************************
*
* CONTENTS: database.h - defines for message IDs
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




/***********************************************************************
*  DEVICE IDs [1..16383]                                               *
***********************************************************************/


/* Frontend Device Ids [129..255]
   ------------------------------                                     */

#define DEV_InvPressDev               129
#define DEV_PlethDev                  130
#define DEV_EcgDev                    131
#define DEV_EcgRespDev                132
#define DEV_TempDev                   133
#define DEV_CardiacOutDev             134
#define DEV_NBPDev                    135
#define DEV_CO2Dev                    136
#define DEV_SaO2PlethDev              137
#define DEV_O2Dev                     138
#define DEV_TcGasDev                  139
#define DEV_TwoChanEegDev             140
#define DEV_SvO2Dev                   141
#define DEV_Ecg12LeadDev              142
#define DEV_Ecg12LeadRespDev          143
#define DEV_BlodAnalyzerDev           144
#define DEV_FourChanEegDev            145
#define DEV_TestDev                   146
#define DEV_BisDev                    147

#define DEV_MagicEcgDev               200

#define DEV_InvEcgDev                 211
#define DEV_GPADev                    212

#define DEV_MipTypeBDev               230
#define DEV_MipTypeCDev               231
#define DEV_MipTypeADev               232

#define DEV_SsCO2Dev                  239
#define DEV_DefibDev                  240
#define DEV_DataTransferDev           242

#define DEV_FelpRecDev                254
#define DEV_EcgSyncOutDev             255


/* Non-Frontend Device Ids [8193..16382]
   -------------------------------------                              */

#define DEV_RtcDev                   8193
#define DEV_EepromDev                8194
#define DEV_AlarmRelayDev            8195
#define DEV_HilDev                   8196
#define DEV_SdnDev                   8197
#define DEV_FeLinkDev                8198
#define DEV_DspcMonoDev              8199
#define DEV_DspcColorPrimDev         8200
#define DEV_DspcColorSecDev          8201
#define DEV_CpuDev                   8202
#define DEV_Rs232Dev                 8203
#define DEV_HdlcDev                  8204
#define DEV_AnalogOutDev             8205
#define DEV_AnalogRs232Dev           8206
#define DEV_RelTimeDev               8207
#define DEV_BatteryDev               8208
#define DEV_LanDev                   8209
#define DEV_XXDspcMonoDev            8210
#define DEV_CsDspcMonoDev            8211
#define DEV_CsDspcColorDev           8212
#define DEV_CLDspcColPrimDev         8213
#define DEV_IfCpuDev                 8214
#define DEV_WBAnalogOutDev           8215


/***********************************************************************
* MODULE IDs [16384..32767]                                            *
***********************************************************************/


/* Special Identification Module Ids
   ---------------------------------                                  */

/*!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
!!  The following Ids are defined as fixed keys for accessing some    !!
!!  global information stored in the central EEPROM.                  !!
!!                                                                    !!
!!  Since only Module Ids may be used as key identifiers to access    !!
!!  data stored in the EEPROM, some extra id numbers are specified    !!
!!  in the MODULE ID section, even if no real ASW module exists.      !!
!!  This ensures an unambiguous access to all EEPROM entries and it   !!
!!  also allows the differenciation between configuration entries     !!
!!  and application module settings.                                  !!
!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!*/

#define Serial_Number               16384   /* monitor serial number  */
#define Eeprom_Number               16385   /* EEPROM identification  */
#define InstrCapability             16386   /* instrument capability
                                               information            */

#define MagicConfig                 16387   /* MAGIC configuration  
                                               block identification   */
#define CentralConfig               16388   /* GENESIS configuration  
                                               block identification   */
#define AuroraConfig                16389   /* AURORA configuration  
                                               block identification   */
#define MOD_DspcInfoMod             16395


/* MERLIN System Software Module Ids
   ---------------------------------                                  */

#define MOD_MerlinOS                16400   /* MERLIN Operating System*/
#define MOD_CloningToolMod          16450   /* see Cloning Tool docs  */


/* MERLIN Application Software Module Ids [16500..24999]
   -----------------------------------------------------              */

#define MOD_GlbInfoHdlMod           16500   /* see GLOBAL INFO HANDLER
                                               documents              */
#define MOD_OpModHdlMod             16501   /* see OPERATING MODE
                                                   HANDLER documents  */

#define MOD_StripRecMod             17000   /* see STRIP REC. docs    */
#define MOD_FelpRecMod              17001   /* see FE_LINK REC. docs  */
#define MOD_RecMgr4WaveMod          17002   /* see REC_MGR documents  */
#define MOD_RecMgr6WaveMod          17003   /* see REC_MGR documents  */
#define MOD_RecMgr8WaveMod          17004   /* see REC_MGR documents  */

#define MOD_AnalogOutMod            17020   /* see ANALOG OUTPUT docs */
#define MOD_MipTypeBMod             17021   /* see MIP documents      */
#define MOD_MipTypeCMod             17022   /* see MIP documents      */
#define MOD_MipTypeAMod             17023   /* see MIP documents      */
#define MOD_VentIfMod               17024
#define MOD_GasAnalyzerMod          17025
#define MOD_AnMachIfMod             17026
#define MOD_VentIf7900Mod           17027
#define MOD_PVLoopsMod              17028
#define MOD_PVLoops7900Mod          17029

#define MOD_Rs232MgrMod             17099   /* see MECIF documents    */
#define MOD_ParServerMod            17100   /* see MECIF documents    */
#define MOD_TwServerMod             17101   /* see MECIF documents    */
#define MOD_ParServLowMod           17102   /* see MECIF documents    */
#define MOD_DAPServerMod            17103
#define MOD_TouchBoardMod           17104
#define MOD_TouchCalibMod           17105

#define MOD_LanMgrMod               17200   /* see LAN documents      */

#define MOD_NumPosMod               18000   /* >>> OBSOLETE MODULE <<<*/
#define MOD_RestTrendsMod           18001   /* see RESTING TRENDS docs*/
#define MOD_ExtendCRGMod            18002   /* see CRG documents      */
#define MOD_BloodAnalyzerMod        18003
#define MOD_FourChanEegMod          18004
#define MOD_ABReviewMod             18005
#define MOD_BloodReviewMod          18006
#define MOD_BisMod                  18007


/* TELEMETRY Application Software Module Ids [25000..25499]
   --------------------------------------------------------------     */

#define MOD_MagicEcgMod             25000   /* MAGIC ECG module       */
#define MOD_MagicHeartMod           25001   /* MAGIC Heart module     */
#define MOD_MagicFeMgrMod           25010   /* MAGIC FeMgr module     */
#define MOD_MagicAlMgrMod           25011   /* MAGIC AlarmMgr module  */
#define MOD_MagicSdnMod             25020   /* MAGIC SDN module       */
#define MOD_MagicGlbInfoMod         25021   /* MAGIC Global Info mod. */
#define MOD_MagicInopMod            25100   /* MAGIC Inop module      */
#define MOD_MagicServiceMod         25101   /* MAGIC Service module   */
#define MOD_MagicMuaMod             25102   /* MAGIC UserAppl module  */


/* CENTRAL STATION Application Software Module Ids [25500..25999]
   --------------------------------------------------------------     */

#define MOD_SdnCommMod              25500   /* see GENESIS documents  */
#define MOD_SdnIntMod               25501   /* see GENESIS documents  */
#define MOD_SdnSysMod               25502   /* see GENESIS documents  */
#define MOD_UpcSysMod               25503   /* see GENESIS documents  */
#define MOD_PrDrMod                 25504   /* see PERSEUS documents  */
#define MOD_UpcAdmitMod             25505   /* see GENESIS documents  */
#define MOD_RdConfigMod             25506   /* see GENESIS documents  */
#define MOD_PrintMgrMod             25507   /* see GENESIS documents  */
#define MOD_AnnotMod                25508   /* see GENESIS documents  */
#define MOD_UpcRdMod                25509   /* see GENESIS documents  */
#define MOD_DelayMgrMod             25510   /* see GENESIS documents  */
#define MOD_UpcRecMod               25511   /* see GENESIS documents  */
#define MOD_GcRecMgrMod             25512   /* see GENESIS documents  */


/* NOMAD Application Software Module Ids [26000 .. 26199]
   ------------------------------------------------------             */

#define MOD_NomadCtlMod             26000   /* NOMAD module           */


/* CATH LAB Application Software Module Ids [26200..26399]
   -------------------------------------------------------            */


/* MERLIN Application Software Module Ids [30000..32766]
   -----------------------------------------------------              */

#define MOD_ExcalUpcEtmMod          32700   /* see XCALIBUR documents */
#define MOD_ExcalUpcEtbMod          32701   /* see XCALIBUR documents */
#define MOD_ExcalUpcCalcMod         32702   /* see XCALIBUR documents */
#define MOD_ExcalUpcEad1Mod         32703   /* see XCALIBUR documents */
#define MOD_ExcalUpcEad2Mod         32704   /* see XCALIBUR documents */
#define MOD_ExcalUpcEphMod          32705   /* see XCALIBUR documents */
#define MOD_ExcalUpcConMod          32706   /* see XCALIBUR documents */
#define MOD_ExcalEdqMod             32707   /* see XCALIBUR documents */
#define MOD_ExcalUpcDrugMod         32708   /* see XCALIBUR documents */
#define MOD_ExcalTransferMod        32709   /* see XCALIBUR documents */
#define MOD_ExcalEdb2Mod            32710   /* see XCALIBUR documents */
#define MOD_ExcalEdb8Mod            32711   /* see XCALIBUR documents */
#define MOD_TabTrendMod             32712
#define MOD_PrinterMod              32713
#define MOD_SnapshotMod             32714
#define MOD_ExcalTransOCMod         32715
#define MOD_AibMod                  32720

#define MOD_SecSaO2PlethMod         32732
#define MOD_SvO2Mod                 32733   /* see SvO2 documents     */
#define MOD_ThreeChanStMod          32734   /* see ST documents       */
#define MOD_SaO2PlethMod            32735
#define MOD_DeltaTempMod            32736
#define MOD_StatPromptHdlMod        32737
#define MOD_ErrorHdlMod             32738   /* see ERROR HANDLER docs */
#define MOD_RestDspMod              32739   /* see MHI documents      */
#define MOD_MhiAocMod               32740   /* see MHI AOC docs       */
#define MOD_DateTimeMod             32741
#define MOD_CardiacOutputMod        32742
#define MOD_RecMgrMod               32743   /* >>> OBSOLETE MODULE <<<*/
#define MOD_ExcalEdb48Mod           32744   /* see XCALIBUR documents */
#define MOD_ExcalEcaMod             32745   /* see XCALIBUR documents */
#define MOD_ExcalEdaMod             32746   /* see XCALIBUR documents */
#define MOD_ExcalEdbMod             32747   /* see XCALIBUR documents */
#define MOD_ExcalEhcMod             32748   /* see XCALIBUR documents */
#define MOD_ExcalEdbOCMod           32749
#define MOD_AlMgrMod                32750   /* see ALARM MANAGER docs.*/
#define MOD_HeartMod                32751
#define MOD_MhiMod                  32752   /* see MHI documents      */
#define MOD_FeMgrMod                32753   /* see FE MANAGER docs    */
#define MOD_InvPressMod             32754
#define MOD_BpToolMod               32755
#define MOD_NBPMod                  32756
#define MOD_TempMod                 32757
#define MOD_DualTempMod             32758
#define MOD_TcGasMod                32759
#define MOD_TestMod                 32760   /* only for debugging     */
#define MOD_TwoChanEegMod           32761   /* !!!!  PRELIMINARY  !!! */
#define MOD_CO2Mod                  32762
#define MOD_O2Mod                   32763
#define MOD_PlethMod                32764   /* >>> OBSOLETE MODULE <<<*/
#define MOD_EcgRespMod              32765
#define MOD_SdnMod                  32766   /* MERLIN SDN module      */


/***********************************************************************
* SOURCE IDs [32768..49151]                                            *
***********************************************************************/

#define SRC_CardiacOutput           32768U  /* see SPI documents      */
#define SRC_CO2                     32769U  /* see SPI documents      */
#define SRC_HeartModule             32770U  /* see SPI documents      */
#define SRC_InvPress                32771U  /* see SPI documents      */
#define SRC_NBP                     32772U  /* see SPI documents      */
#define SRC_O2                      32773U  /* see SPI documents      */
#define SRC_Pleth                   32774U  /* see SPI documents      */
#define SRC_Resp                    32775U  /* see SPI documents      */
#define SRC_SaO2                    32776U  /* see SPI documents      */
#define SRC_Temp                    32777U  /* see SPI documents      */
#define SRC_ThreeChanEcg            32778U  /* see SPI documents      */
#define SRC_DeltaTemp               32779U  /* see SPI documents      */
#define SRC_Wedge                   32780U  /* see SPI documents      */
#define SRC_EcgPacer                32781U  /* see SPI documents      */
#define SRC_ThreeChanSt             32782U  /* see SPI documents      */
#define SRC_TwoChanEeg              32783U  /* !!!!  PRELIMINARY  !!! */
#define SRC_BpTool                  32784U  /* see SPI documents      */
#define SRC_Cpp                     32785U  /* see SPI documents      */
#define SRC_TcpO2                   32786U  /* see SPI documents      */
#define SRC_TcpCO2                  32787U  /* see SPI documents      */
#define SRC_SvO2                    32788U  /* see SPI documents      */
#define SRC_BloodAnalyzer           32789U
#define SRC_FourChanEeg             32790U
#define SRC_BloodReview             32791U
#define SRC_CCO                     32792U
#define SRC_Bis                     32793U

#define SRC_Sdn                     32800U  /* see SDN documents      */
#define SRC_Mhi                     32801U  /* see MHI documents      */
#define SRC_FeMgr                   32802U  /* see MHI documents      */
#define SRC_AlMgr                   32803U  /* see ALARM_MGR ERS      */
#define SRC_Arrhy                   32804U  /* see SDN documents      */
#define SRC_VCP                     32805U  /* see VTP/VCP documents  */
#define SRC_RecMgr                  32806U  /* see REC_MGR documents  */
#define SRC_DateTime                32807U  /* see DATE & TIME docs   */
#define SRC_Aoc                     32808U  /* see MHI AOC docs       */
#define SRC_RestDsp                 32809U  /* see MHI documents      */
#define SRC_ErrorHdl                32810U  /* see ERROR HANDLER docs */
#define SRC_StatPromptHdl           32811U
#define SRC_Loa                     32812U  /* see MHI documents      */
#define SRC_RestingTrends           32813U  /* see RESTING TRENDS docs*/
#define SRC_ExtendCRG               32814U  /* see CRG documents      */
#define SRC_RvtApp                  32815U  /* see VTP/VCP documents  */
#define SRC_GlbInfoHdl              32816U  /* see GLB INFO HANDL docs*/
#define SRC_OpModHdl                32817U  /* see OP MODE HANDL docs */
#define SRC_CalTest                 32818U  /* see OP MODE HANDL docs */
#define SRC_NumPos                  32819U  /* see MHI documents      */
#define SRC_Excalibur               32820U  /* see XCALIBUR documents */
#define SRC_ExcalUpcCalc            32821U  /* see XCALIBUR documents */
#define SRC_ExcalUpcTab             32822U  /* see XCALIBUR documents */
#define SRC_ExcalUpcTrend           32823U  /* see XCALIBUR documents */
#define SRC_ExcalUpcEad1            32824U  /* see XCALIBUR documents */
#define SRC_ExcalUpcEad2            32825U  /* see XCALIBUR documents */
#define SRC_ExcalUpcEph             32826U  /* see XCALIBUR documents */
#define SRC_ExcalEca                32827U  /* see XCALIBUR documents */
#define SRC_ExcalEdb                32828U  /* see XCALIBUR documents */
#define SRC_ExcalEda                32829U  /* see XCALIBUR documents */
#define SRC_ExcalEhc                32830U  /* see XCALIBUR documents */
#define SRC_ExcalUpcConfg           32832U  /* see XCALIBUR documents */
#define SRC_ExcalEdq                32833U  /* see XCALIBUR documents */
#define SRC_ExcalTransf             32834U  /* see XCALIBUR documents */
#define SRC_ExcalDrug               32835U  /* see XCALIBUR documents */
#define SRC_Aib                     32836U
#define SRC_TabTrend                32837U
#define SRC_Printer                 32838U
#define SRC_Snapshot                32839U

#define SRC_DelRecMgr               32840U
#define SRC_RtRecMgr                32841U
#define SRC_RecWaves                32842U
#define SRC_StoRecMgr               32843U

#define SRC_StripRec                32850U  /* see STRIP REC docs     */
#define SRC_SdnRec                  32851U
#define SRC_FelpRec                 32852U  /* see FE LINK REC docs   */

#define SRC_GasAnalyzerCO2          32854U
#define SRC_GasAnalyzerO2           32855U
#define SRC_GasAnalyzerN2O          32856U
#define SRC_GasAnalyzerAgt          32857U

#define SRC_VentIf                  32859U
#define SRC_AnalogOut               32860U  /* see ANALOG OUTPUT docs */
#define SRC_MipTypeB                32861U  /* see MIP documents      */
#define SRC_MipTypeC                32862U  /* see MIP documents      */
#define SRC_MipTypeA                32863U  /* see MIP documents      */
#define SRC_Rs232Mgr                32864U  /* see MECIF documents    */
#define SRC_ParServer               32865U  /* see MECIF documents    */
#define SRC_TwServer                32866U  /* see MECIF documents    */
#define SRC_DAPServer               32867U
#define SRC_ABReview                32868U
#define SRC_AnMachIf                32869U
#define SRC_LanMgr                  32870U  /* see LAN documents      */
#define SRC_PVLoops                 32871U
#define SRC_TouchBoard              32872U
#define SRC_TouchCalib              32873U


/*----- additional Source Ids for TELEMETRY [35000..35499] -----------*/

#define SRC_MagicEcg                35000U  /* MAGIC Ecg              */
#define SRC_Inop                    35001U  /* MAGIC Inop             */
#define SRC_Service                 35002U  /* MAGIC Service          */
#define SRC_Mua                     35003U  /* MAGIC User Application */


/*----- additional Source Ids for CENTRAL STATION [35500..35999] -----*/

#define SRC_SdnComm                 35500U  /* see GENESIS documents  */
#define SRC_SdnInt                  35501U  /* see GENESIS documents  */
#define SRC_SdnSys                  35502U  /* see GENESIS documents  */
#define SRC_UpcSys                  35503U  /* see GENESIS documents  */
#define SRC_PrDr                    35504U  /* see PERSEUS documents  */
#define SRC_UpcAdmit                35505U  /* see GENESIS documents  */
#define SRC_RdConfig                35506U  /* see GENESIS documents  */
#define SRC_PrintMgr                35507U  /* see GENESIS documents  */
#define SRC_Annot                   35508U  /* see GENESIS documents  */
#define SRC_UpcRd                   35509U  /* see GENESIS documents  */
#define SRC_DelayMgr                35510U  /* see GENESIS documents  */
#define SRC_UpcRec                  35511U  /* see GENESIS documents  */
#define SRC_RecReq                  35512U


/*---------- additional Source Ids for NOMAD [36000..36099] ----------*/

#define SRC_NomadCtl                36000U  /* see NOMAD documents    */


/*--------- additional Source Ids for CATH LAB [36100..36199] --------*/


#define SRC_Unspec                  49151U  /* unspecified source     */



/***********************************************************************
* STATIC IDs [49152..65534]                                            *
***********************************************************************/


/* Static Links between Application Modules and Frontend Devices
   -------------------------------------------------------------      */

#define STA_FeInvPress              49152U
#define STA_FePleth                 49153U  /* >>> OBSOLETE MODULE <<<*/
#define STA_FeEcg                   49154U
#define STA_FeEcgResp               49155U
#define STA_FeTemp                  49156U
#define STA_FeCardiacOut            49157U
#define STA_FeNBP                   49158U
#define STA_FeCO2                   49159U
#define STA_FeSaO2Pleth             49160U
#define STA_FeO2                    49161U
#define STA_FeFelpRec               49162U
#define STA_FeTwoChanEeg            49163U  /* !!!!  PRELIMINARY  !!! */
#define STA_FeMipTypeB              49164U
#define STA_FeMipTypeC              49165U
#define STA_FeMipTypeA              49166U
#define STA_FeTcGas                 49167U
#define STA_FeSvO2                  49168U
#define STA_FeBloodAnalyzer         49169U
#define STA_FeFourChanEeg           49170U
#define STA_FeTest                  49171U
#define STA_CRGLink                 49172U

#define STA_FeInvEcg                49300U
#define STA_FeGPA                   49301U

#define STA_FeMagicEcg              49500U  /* see MAGIC documents    */

#define STA_FeSsCO2                 49599U
#define STA_FeDataTransfer          49600U
#define STA_FeBis                   49601U


/* Static Links between Application Modules and Non-Frontend Devices
   -----------------------------------------------------------------  */

#define STA_SdnLink                 50000U
#define STA_FeLink                  50001U
#define STA_MhiDspcLink             50002U
#define STA_Rs232Link               50003U
#define STA_HdlcLink                50004U
#define STA_AlRelayLink             50005U
#define STA_AnalogOutLink           50006U
#define STA_BatteryLink             50007U  /* see NOMAD documents    */
#define STA_WBAnOutLink             50008U


/* Static Links between two Application Modules
   --------------------------------------------                       */

#define STA_NumPosMhiLink           60000U  /* see MHI documents      */
#define STA_RestDspMhiLink          60001U  /* see MHI documents      */
#define STA_DAPLink                 60002U



/***********************************************************************
* CHANNEL IDs [32768..49151]                                           *
***********************************************************************/

#define CHA_AWRR                    32768U  /* see SPI documents      */
#define CHA_BeatDetect              32769U  /* see SPI documents      */
#define CHA_CO2                     32770U  /* see SPI documents      */
#define CHA_CuffPress               32771U  /* see SPI documents      */
#define CHA_ETCO2                   32772U  /* see SPI documents      */
#define CHA_EarlySyst               32773U  /* see SPI documents      */
#define CHA_Ecg                     32774U  /* see SPI documents      */
#define CHA_FIO2                    32775U  /* see SPI documents      */
#define CHA_General                 32776U  /* see SPI documents      */
#define CHA_HeartRate               32777U  /* see SPI documents      */
#define CHA_IMCO2                   32778U  /* see SPI documents      */
#define CHA_OxygenSatur             32779U  /* see SPI documents      */
#define CHA_Pleth                   32780U  /* see SPI documents      */
#define CHA_Press                   32781U  /* see SPI documents      */
#define CHA_PressNum                32782U  /* see SPI documents      */
#define CHA_Pulse                   32783U  /* see SPI documents      */
#define CHA_PulseRate               32784U  /* see SPI documents      */
#define CHA_RelativePerf            32785U  /* see SPI documents      */
#define CHA_RespDetect              32786U  /* see SPI documents      */
#define CHA_Temp                    32787U  /* see SPI documents      */
#define CHA_CardiacOutput           32788U  /* see SPI documents      */
#define CHA_Resp                    32789U  /* see SPI documents      */
#define CHA_St                      32790U  /* see SPI documents      */
#define CHA_Eeg                     32791U  /* !!!!  PRELIMINARY  !!! */
#define CHA_TcpO2                   32792U  /* see SPI documents      */
#define CHA_TcpCO2                  32793U  /* see SPI documents      */
#define CHA_SiteTime                32794U  /* see SPI documents      */
#define CHA_SvO2                    32795U  /* see SPI documents      */
#define CHA_Quality                 32796U  /* see SPI documents      */
#define CHA_DeltaOxygenSat          32797U  /* see SPI documents      */
#define CHA_BloodNum                32798U

#define CHA_Arrhy                   32800U  /* see SDN documents      */
#define CHA_Overview                32801U  /* see SDN documents      */
#define CHA_AutoAlDsp               32802U  /* see SDN documents      */
#define CHA_EctSta                  32803U  /* see SDN documents      */
#define CHA_RhySta                  32804U  /* see SDN documents      */
#define CHA_Vpb                     32805U  /* see SDN documents      */
#define CHA_SdnText                 32806U  /* see SDN documents      */
#define CHA_SdnTime                 32807U  /* see SDN documents      */

#define CHA_Text                    32809U
#define CHA_Config                  32810U
#define CHA_Device                  32811U
#define CHA_Fast                    32812U
#define CHA_Slow                    32813U
#define CHA_Control                 32814U
#define CHA_Scan                    32815U
#define CHA_Sort                    32816U
#define CHA_Async                   32817U
#define CHA_Sync                    32818U
#define CHA_Test                    32819U
#define CHA_AlRes                   32820U  /* see ALARM_MGR ERS      */
#define CHA_AlStat                  32821U  /* see ALARM_MGR ERS      */
#define CHA_AlText                  32822U  /* see ALARM_MGR ERS      */
#define CHA_InText                  32823U  /* see ALARM_MGR ERS      */
#define CHA_NumEnh                  32824U  /* see ALARM_MGR ERS      */
#define CHA_NurseRelay              32825U  /* see ALARM_MGR ERS      */
#define CHA_RemSil                  32826U  /* see ALARM_MGR ERS      */
#define CHA_ToHil                   32827U  /* see ALARM_MGR ERS      */
#define CHA_ToFields                32828U
#define CHA_MhiKeys                 32829U
#define CHA_MipWave                 32830U  /* see MIP documents      */
#define CHA_MipNum                  32831U  /* see MIP documents      */
#define CHA_RemSus                  32832U  /* see ALARM_MGR ERS      */
#define CHA_VentNum                 32833U
#define CHA_Data                    32834U
#define CHA_VentWave                32835U
#define CHA_AnMachNum               32836U
#define CHA_AnMachWave              32837U

#define CHA_DAP                     32839U
#define CHA_VCP                     32840U  /* see VTP/VCP documents  */
#define CHA_LVT                     32841U  /* see VTP/VCP documents  */
#define CHA_RVT                     32842U  /* see VTP/VCP documents  */
#define CHA_DBQ                     32843U  /* see VTP/VCP documents  */

#define CHA_Etm                     32844U  /* see EXCALIBUR docs     */
#define CHA_Etb                     32845U  /* see EXCALIBUR docs     */
#define CHA_UpcCalc                 32846U  /* see EXCALIBUR docs     */
#define CHA_Ead1                    32847U  /* see EXCALIBUR docs     */
#define CHA_Eph                     32849U  /* see EXCALIBUR docs     */
#define CHA_Edb                     32850U  /* see EXCALIBUR docs     */
#define CHA_Eda                     32851U  /* see EXCALIBUR docs     */
#define CHA_Ehc                     32852U  /* see EXCALIBUR docs     */
#define CHA_Eup                     32853U  /* see EXCALIBUR docs     */
#define CHA_Eca                     32854U  /* see EXCALIBUR docs     */
#define CHA_Edq                     32855U  /* see EXCALIBUR docs     */
#define CHA_Edt                     32856U  /* see EXCALIBUR docs     */

#define CHA_Recorder                32860U  /* see REC_MGR documents  */
#define CHA_Delayed                 32861U  /* see REC_MGR documents  */
#define CHA_Realtime                32862U  /* see REC_MGR documents  */
#define CHA_Recording               32863U

#define CHA_DateTime                32870U  /* see DATE & TIME docs   */
#define CHA_Settings                32871U  /* see SETTINGS TRANSFER  */
#define CHA_Internal                32872U
#define CHA_MhiSnap                 32873U


/*-------- additional Channel Ids for TELEMETRY [3xxxx..33399] -------*/

#define CHA_Stored                  32880U  /* see MAGIC documents    */
#define CHA_InopCtl                 32890U  /* see MAGIC documents    */
#define CHA_InopRd                  32891U  /* see MAGIC documents    */


/*----- additional Channel Ids for CENTRAL STATION [33400..33999] ----*/

#define CHA_Gbn1                    33440U  /* see GENESIS documents  */
#define CHA_Gbn2                    33441U  /* see GENESIS documents  */
#define CHA_Gbn3                    33442U  /* see GENESIS documents  */
#define CHA_Gbn4                    33443U  /* see GENESIS documents  */
#define CHA_Gbn5                    33444U  /* see GENESIS documents  */
#define CHA_Gbn6                    33445U  /* see GENESIS documents  */
#define CHA_Gbn7                    33446U  /* see GENESIS documents  */
#define CHA_Gbn8                    33447U  /* see GENESIS documents  */
#define CHA_Gbn9                    33448U  /* see GENESIS documents  */
#define CHA_Gbn10                   33449U  /* see GENESIS documents  */
#define CHA_Gbn11                   33450U  /* see GENESIS documents  */
#define CHA_Gbn12                   33451U  /* see GENESIS documents  */
#define CHA_NoGbn                   33452U  /* see GENESIS documents  */
#define CHA_DelayAssign             33453U  /* see GENESIS documents  */
#define CHA_DelayDeassign           33454U  /* see GENESIS documents  */


/*---------- additional Channel Ids for NOMAD [34000..34199] ---------*/


/*-------- additional Channel Ids for CATH LAB [34200..34399] --------*/


/*----------- more Channel Ids for MERLIN [40000..49151] -------------*/

#define CHA_N2O                     40000U  /* see SPI documents      */
#define CHA_INN2O                   40001U  /* see SPI documents      */
#define CHA_ETN2O                   40002U  /* see SPI documents      */
#define CHA_Agent                   40003U  /* see SPI documents      */
#define CHA_INAgent                 40004U  /* see SPI documents      */
#define CHA_ETAgent                 40005U  /* see SPI documents      */
#define CHA_O2                      40006U  /* see SPI documents      */
#define CHA_INO2                    40007U  /* see SPI documents      */
#define CHA_ETO2                    40008U  /* see SPI documents      */

#define CHA_Eeg1                    40020U
#define CHA_Eeg2                    40021U
#define CHA_Eeg3                    40022U
#define CHA_Eeg4                    40023U

/* New  channels for the pCCO parameter.                              */

#define CHA_CCO                     40024U
#define CHA_CCI                     40025U
#define CHA_SVR                     40026U
#define CHA_SV                      40027U
#define CHA_SI                      40028U
#define CHA_SVRI                    40029U

#define CHA_ITBV                    40031U
#define CHA_EVLW                    40032U
#define CHA_CFI                     40033U

#define CHA_Bis                     40034U

#define CHA_RdaConfig               40030U

#define CHA_Touch                   40035U
#define CHA_Mouse                   40036U

#define CHA_Emg                     40037U
#define CHA_Sqi                     40038U

#define CHA_Unspec                  49151U  /* unspecified channel    */


/***********************************************************************
* DYNAMIC IDs [49152..65534]                                           *
***********************************************************************/

#define DYN_Twa                     49152U  /* see TPL documents      */

#define DYN_RecAccess               49153U  /* see REC_MGR documents  */
#define DYN_StoredAccess            49154U  /* see REC_MGR documents  */

#define DYN_SpiGp                   49155U  /* see SPI documents      */
#define DYN_Eeprom                  49156U  /* see EEPROM handling    */
#define DYN_Rda                     49157U  /* see MHI documents      */
#define DYN_Query                   49158U  /* see MIP documents      */
#define DYN_SettTransf              49159U  /* see SETTINGS TRANSFER  */

#define DYN_LvtWvCtl                49160U  /* see VTP/VCP documents  */

#define DYN_MUAtpl                  49161U  /* see MAGIC Mua docs     */
#define DYN_SDNrec                  49162U  /* see MAGIC Recorder ES  */

#define DYN_Lrp                     49170U  /* see LOG REC COMM docs  */

#define DYN_MecifGp                 49200U  /* see MECIF documents    */
#define DYN_MecifCtl                49201U  /* see MECIF documents    */
#define DYN_CrossLinkKey            49202U
#define DYN_PrintAccess             49203U


/*-- additional Dynamic Link Ids for CENTRAL STATION [50000..50099] --*/

/*------ additional Dynamic Link Ids for CATH LAB [50100..50199] -----*/


/*!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
!!  The below Dynamic Link Id can be used by an ASW to get headers    !!
!!  from the MOS that can be used as response headers for several     !!
!!  purposes                                                          !!
!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!*/

#define DYN_GetHeader               65500U  /* general purpose link id*/


/***********************************************************************
* TYPE IDs [1..65534]                                                  *
***********************************************************************/

#define TYP_SpiPS                       1U  /* see SPI documents      */
#define TYP_SpiCW                       2U  /* see SPI documents      */
#define TYP_SpiWS                       3U  /* see SPI documents      */
#define TYP_SpiEV                       4U  /* see SPI documents      */
#define TYP_SpiBV                       5U  /* see SPI documents      */
#define TYP_SpiBS                       6U  /* see SPI documents      */
#define TYP_SpiNU                       7U  /* see SPI documents      */
#define TYP_SpiST                       8U  /* see SPI documents      */
#define TYP_SpiPR                       9U  /* see SPI documents      */
#define TYP_SpiAL                      10U  /* see SPI documents      */
#define TYP_SpiIN                      11U  /* see SPI documents      */
#define TYP_SpiOR                      13U  /* see SPI documents      */
#define TYP_SpiOP                      14U  /* see SPI documents      */
#define TYP_SpiTR                      15U  /* see SPI documents      */
#define TYP_SpiAN                      16U  /* see SPI documents      */
#define TYP_SpiMX                      17U  /* see SPI documents      */
#define TYP_SpiIC                      18U  /* see SPI documents      */

#define TYP_SdnBedlb                   20U  /* see SDN documents      */
#define TYP_SdnPni                     21U  /* see SDN documents      */
#define TYP_SdnTime                    22U  /* see SDN documents      */
#define TYP_SdnCW                      23U  /* see SDN documents      */
#define TYP_SdnWS                      24U

#define TYP_Data                       25U  /* see TPL documents      */
#define TYP_Req                        26U  /* see TPL documents      */
#define TYP_Rsp                        27U  /* see TPL documents      */

#define TYP_Key                        30U  /* see MHI documents      */

#define TYP_EcgEasi                    31U

#define TYP_AppOpnReq                  40U  /* see VTP/VCP documents  */
#define TYP_AppClsReq                  41U  /* see VTP/VCP documents  */
#define TYP_AppOpnChk                  42U  /* see VTP/VCP documents  */
#define TYP_AppClsChk                  43U  /* see VTP/VCP documents  */
#define TYP_Wv1Ctl                     44U  /* see VTP/VCP documents  */
#define TYP_Wv2Ctl                     45U  /* see VTP/VCP documents  */
#define TYP_LVTCtl                     46U  /* see VTP/VCP documents  */
#define TYP_VTWvAnnot                  47U  /* see VTP/VCP documents  */

#define TYP_Edb                        50U  /* see EXCALIBUR docs     */
#define TYP_Eda                        51U  /* see EXCALIBUR docs     */
#define TYP_Ehc                        52U  /* see EXCALIBUR docs     */
#define TYP_Eup                        53U  /* see EXCALIBUR docs     */
#define TYP_Eca                        54U  /* see EXCALIBUR docs     */
#define TYP_Edt                        55U  /* see EXCALIBUR docs     */
#define TYP_Eph                        56U  /* see EXCALIBUR docs     */
#define TYP_Dyn                        58U  /* see EXCALIBUR docs     */
#define TYP_Bdcst                      59U  /* see EXCALIBUR docs     */
#define TYP_Etm                        60U  /* see EXCALIBUR docs     */
#define TYP_Etb                        61U  /* see EXCALIBUR docs     */
#define TYP_UpcCalc                    62U  /* see EXCALIBUR docs     */
#define TYP_Ead1                       63U  /* see EXCALIBUR docs     */
#define TYP_Edq                        65U  /* see EXCALIBUR docs     */

#define TYP_Change                     68U
#define TYP_Error                      69U
#define TYP_Status                     70U
#define TYP_Control                    71U
#define TYP_Info                       72U
#define TYP_Input                      73U
#define TYP_Output                     74U

#define TYP_Capability                 75U  /* see LOG REC COMM docs  */
#define TYP_RecOpReq                   76U  /* see LOG REC COMM docs  */
#define TYP_RecOpChk                   77U  /* see LOG REC COMM docs  */
#define TYP_Config                     78U  /* see RECORD WAVES docs  */

#define TYP_MhiOSAl                    80U  /* see MHI documents     */
#define TYP_MhiOSCapt                  81U  /* see MHI documents     */
#define TYP_MhiOSInstr                 82U  /* see MHI documents      */
#define TYP_MhiOSMark                  83U  /* see MHI documents      */
#define TYP_MhiOSMon                   84U  /* see MHI documents      */
#define TYP_MhiOSOvw                   85U  /* see MHI documents      */
#define TYP_MhiOSPar                   86U  /* see MHI documents      */
#define TYP_MhiOSPat                   87U  /* see MHI documents      */
#define TYP_MhiOSRec                   88U  /* see MHI documents      */
#define TYP_Numerics                   90U  /* see MHI documents      */
#define TYP_Speeds                     91U  /* see MHI documents      */
#define TYP_TraceMode                  92U  /* see MHI documents      */
#define TYP_Waves                      93U  /* see MHI documents      */
#define TYP_Colors                     94U  /* see MHI documents      */

#define TYP_MakInternal               100U  /* see MAK documents      */

#define TYP_AibReq                    110U
#define TYP_AibRsp                    111U

#define TYP_RVTAppl                   120U  /* see VTP/VCP documents  */
#define TYP_RVTGen                    121U  /* see VTP/VCP documents  */
#define TYP_RVTDspCmplt               122U  /* see VTP/VCP documents  */
#define TYP_RVTAppStChk               123U  /* see VTP/VCP documents  */
#define TYP_RVTSesUpdChk              124U  /* see VTP/VCP documents  */
#define TYP_RVTDspUpdChk              125U  /* see VTP/VCP documents  */
#define TYP_RVTDspCtlChk              126U  /* see VTP/VCP documents  */
#define TYP_RVTWvAnnReq               127U  /* see VTP/VCP documents  */

#define TYP_Isu                       128U  /* preliminary MAGIC sdn  */
#define TYP_PicCtl                    132U  /* preliminary MAGIC sdn  */
#define TYP_CtlReq                    134U  /* MAGIC MUA module       */
#define TYP_CtlChk                    135U  /* MAGIC MUA module       */
#define TYP_MUAChk                    136U  /* MAGIC MUA module       */
#define TYP_LdSetCtl                  137U  /* MAGIC MUA module       */
#define TYP_ExtMonCtl                 138U  /* MAGIC MUA module       */
#define TYP_Vcp                       141U  /* MAGIC MUA module       */
#define TYP_Vtp                       142U  /* MAGIC MUA module       */
#define TYP_BedCtl                    144U  /* MAGIC MUA module       */
#define TYP_DlyReq                    150U  /* see MAGIC Recorder ES  */
#define TYP_DlyChk                    151U  /* see MAGIC Recorder ES  */
#define TYP_StoredReq                 152U  /* see MAGIC Recorder ES  */
#define TYP_StoredChk                 153U  /* see MAGIC Recorder ES  */
#define TYP_AnnotReq                  154U  /* see MAGIC Recorder ES  */
#define TYP_StopReq                   156U  /* see MAGIC Recorder ES  */
#define TYP_StopChk                   157U  /* see MAGIC Recorder ES  */
#define TYP_StopNotif                 158U  /* see MAGIC Recorder ES  */
#define TYP_ClrReq                    160U  /* see MAGIC Inop docs    */
#define TYP_ClrChk                    161U  /* see MAGIC Inop docs    */
#define TYP_FilterReq                 162U  /* see MAGIC Inop docs    */
#define TYP_FilterChk                 163U  /* see MAGIC Inop docs    */
#define TYP_DataReq                   164U  /* see MAGIC Inop docs    */
#define TYP_DataChk                   165U  /* see MAGIC Inop docs    */
#define TYP_TotalsReq                 166U  /* see MAGIC Inop docs    */
#define TYP_TotalsChk                 167U  /* see MAGIC Inop docs    */

#define TYP_VolCtl                    168U  /* see NOMAD documents    */


/*------- additional Type Ids for CENTRAL STATION [1000..1999] -------*/

#define TYP_DemoReq                  1000U  /* Patient demograph. req.*/
#define TYP_Demographics             1001U  /* Patient demographics   */
#define TYP_SdnBranch                1002U  /* SDN branch number      */
#define TYP_SdnDspAssign             1003U  /* SDN display sector
                                               assignment             */
#define TYP_SdnUnitStat              1004U  /* SDN unit status        */
#define TYP_PerseusMap               1005U  /* Perseus channel map    */
#define TYP_GbnAssign                1006U
#define TYP_GbnUnassign              1007U
#define TYP_FiltTime                 1008U  /* filtered SDN time      */
#define TYP_Csa                      1009U  /* SDN Central Station
                                               arrhythmia             */
#define TYP_GbnInfo                  1010U  /* Gbn Information        */
#define TYP_GbnOnOff                 1011U
#define TYP_LbnGbnMap                1012U
#define TYP_NetCensus                1013U  /* SDN Network Census     */
#define TYP_SdnOnline                1014U  /* SDN Online Notification*/
#define TYP_TuneWv                   1015U  /* Tune SDN Wave          */
#define TYP_TuneWvAck                1016U  /* Tune SDN Wave Ack      */
#define TYP_StWvStop                 1017U
#define TYP_SdnBL                    1018U  /* SDN Bed Label          */
#define TYP_PkdSdnBL                 1019U  /* Packed SDN Bed Label   */
#define TYP_PkdSdnPni                1020U  /* Packed SDN Patient Name*/
#define TYP_SdnAS                    1021U  /* SDN alert status       */
#define TYP_SdnAT                    1022U  /* SDN alarm text         */
#define TYP_PkdSdnAT                 1023U  /* Packed SDN alarm text  */
#define TYP_SdnIT                    1024U  /* SDN inop text          */
#define TYP_PkdSdnIT                 1025U  /* Packed SDN inop text   */
#define TYP_SdnAR                    1026U  /* SDN alarm reset        */
#define TYP_SdnWV                    1027U  /* SDN wave value         */
#define TYP_PkdSdnWV                 1028U  /* Packed SDN wave value  */
#define TYP_SdnWSD                   1029U  /* SDN wave support data  */
#define TYP_PkdSdnWSD                1030U  /* Packed SDN wave support
                                               Data                   */
#define TYP_SdnPV                    1031U  /* SDN parameter value    */
#define TYP_SdnPSD                   1032U  /* SDN parameter support
                                               data                   */
#define TYP_PkdSdnPSD                1033U  /* Packed SDN PSD         */
#define TYP_Isb                      1034U  /* SDN instrument status
                                                - bed                 */
#define TYP_Isr                      1035U  /* SDN instrument status
                                                - recorder            */
#define TYP_PkdIsr                   1036U  /* Packed SDN IS-R        */
#define TYP_PkdIsu                   1037U  /* Packed SDN IS-U        */
#define TYP_MaintStatus              1038U  /* SDN Maintenance Status */
#define TYP_PkdCsa                   1039U  /* Packed Central Station
                                               arrhythmia             */
#define TYP_SdnSwHdr                 1040U  /* SDN stored wave hdrs   */
#define TYP_SdnBackoff               1041U  /* SDN backoff            */
#define TYP_RecReq                   1042U  /* Record request         */
#define TYP_RecOp                    1043U  /* Record operate         */
#define TYP_TxReq                    1044U
#define TYP_Ws                       1045U
#define TYP_DataCap                  1046U
#define TYP_AnnotData                1047U
#define TYP_FormName                 1048U
#define TYP_PerConf                  1049U
#define TYP_SectorAssign             1050U
#define TYP_DisplayForm              1051U
#define TYP_HRLimits                 1052U
#define TYP_Discharge                1053U


/*---------- additional Type Ids for CATH LAB [2000..2999] -----------*/


#define TYP_Int                     65388U  /* preliminary            */
#define TYP_Rw                      65492U  /* see FE LINK documents  */
#define TYP_Priv                    65501U  /* preliminary            */
#define TYP_Unspec                  65534U  /* unspecified type       */

#endif


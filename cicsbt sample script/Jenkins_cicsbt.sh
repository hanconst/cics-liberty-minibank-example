#Export system variables for CICS build toolkit
export CICSBT_HOME=C:/cicsbt-5.3.8/cicsbt
export CICSBT_OUTPUT=$CICSBT_HOME/output
export CICSBT_PROP=$CICSBT_HOME/props

#Hide command output and prevent from immediate EXIT in case of none zero exit code
set +xe

#Run cicsbt to build two CICS bundles from Jenkins workspace where Git source codes are fetched. As the source projects contain variables, and thus the output bundles are unresolved. You can use them directly.
#Please change team20 to your own team name, e.g. team01
$CICSBT_HOME/cicsbt --input $WORKSPACE/com.ibm.cics.minibank.* --build com.ibm.cics.minibank.WOR.application.bundle com.ibm.cics.minibank.AOR.application.bundle --output $CICSBT_OUTPUT/team20/unresolved --target com.ibm.cics.explorer.sdk.web.liberty53.target
rc=$?
if [ $rc -eq 0 ]; then
    echo "cicsbt build has completed successfully with no error or warning."
elif [ $rc -eq 1 ]; then
    echo "cicsbt build has completed successfully with warning" 
    echo "Return code from cicsbt is " $rc
    echo "Description: Warning. Some source projects were not imported."
elif [ $rc -eq 2 ]; then
    echo "cicsbt build completed successfully with warning"
    echo "Return code from cicsbt is " $rc
    echo "Description: Warning. Artifacts were built, with warnings."
else
    echo "cicsbt build failed with return code " $rc
    echo "Please check log."
    exit
fi

#Run cicsbt to resolve the varibales in the unresolved bundles.
#Please change team20 to your own team name, e.g. team01
$CICSBT_HOME/cicsbt --resolve $CICSBT_OUTPUT/team20/unresolved --output $CICSBT_OUTPUT/team20/resolved --properties $CICSBT_PROP/team20.properties
rc2=$?
if [ $rc2 -eq 0 ]; then
    echo "cicsbt resolve completed successfully with no error or warning."
else
    echo "cicsbt resolve failed with return code " $rc2
    echo "Please check log."
fi
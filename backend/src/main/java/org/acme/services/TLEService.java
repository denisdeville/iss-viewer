package org.acme.services;

import java.io.File;

import org.hipparchus.util.FastMath;
import org.orekit.bodies.BodyShape;
import org.orekit.bodies.GeodeticPoint;
import org.orekit.bodies.OneAxisEllipsoid;
import org.orekit.data.DataContext;
import org.orekit.data.DataProvidersManager;
import org.orekit.data.DirectoryCrawler;
import org.orekit.frames.Frame;
import org.orekit.frames.FramesFactory;
import org.orekit.propagation.analytical.tle.TLE;
import org.orekit.propagation.analytical.tle.TLEPropagator;
import org.orekit.time.AbsoluteDate;
import org.orekit.time.TimeScalesFactory;
import org.orekit.utils.Constants;
import org.orekit.utils.IERSConventions;
import org.orekit.utils.PVCoordinates;

import java.time.LocalDateTime;

public class TLEService {

    public static TLEService instance;

    public static TLEService getInstance() {
        if (instance == null) {
            instance = new TLEService("1 25544U 98067A   23081.03898101  .00015358  00000+0  28263-3 0  9999", "2 25544  51.6417  40.2943 0006096 117.6201   7.3764 15.49366819388274");
        }
        return instance;
    }

    private TLE tle;
    private Frame earthFrame;
    private TLEPropagator propagator;
    private BodyShape earth;
    private AbsoluteDate absoluteDate;

    private TLEService(String tleLine1, String tleLine2) {
        File orekitData = new File("orekit-data");
        DataProvidersManager manager = DataContext.getDefault().getDataProvidersManager();
        manager.addProvider(new DirectoryCrawler(orekitData));
        
        //=======================================================//
        tle = new TLE(tleLine1, tleLine2);
        //Get an unspecified International Terrestrial Reference Frame.
        earthFrame = FramesFactory.getITRF(IERSConventions.IERS_2010, true);
        //Modeling of a one-axis ellipsoid.
        //One-axis ellipsoids is a good approximate model for most planet-size and larger natural bodies.
        earth = new OneAxisEllipsoid(Constants.WGS84_EARTH_EQUATORIAL_RADIUS,
                Constants.WGS84_EARTH_FLATTENING,
                earthFrame);
        propagator = TLEPropagator.selectExtrapolator(tle);
    }

    public double[] getLatitudeLongitude(LocalDateTime dateTime) {
        absoluteDate = new AbsoluteDate(
                dateTime.getYear(),
                dateTime.getMonthValue(),
                dateTime.getDayOfMonth(),
                dateTime.getHour(),
                dateTime.getMinute(),
                dateTime.getSecond(),
                TimeScalesFactory.getUTC());

        PVCoordinates pvCoordinates = propagator.propagate(absoluteDate).getPVCoordinates(earthFrame);
        GeodeticPoint geodeticPoint = earth.transform(
                pvCoordinates.getPosition(),
                earthFrame,
                absoluteDate);
        double actualLatitude = FastMath.toDegrees(geodeticPoint.getLatitude());
        double actualLongitude = FastMath.toDegrees(geodeticPoint.getLongitude());

        return new double[]{actualLatitude, actualLongitude};
    }


}

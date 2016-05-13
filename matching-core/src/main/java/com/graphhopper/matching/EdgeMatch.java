/*
 *  Licensed to GraphHopper and Peter Karich under one or more contributor
 *  license agreements. See the NOTICE file distributed with this work for 
 *  additional information regarding copyright ownership.
 * 
 *  GraphHopper licenses this file to you under the Apache License, 
 *  Version 2.0 (the "License"); you may not use this file except in 
 *  compliance with the License. You may obtain a copy of the License at
 * 
 *       http://www.apache.org/licenses/LICENSE-2.0
 * 
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package com.graphhopper.matching;

import com.graphhopper.util.EdgeIteratorState;
import com.graphhopper.util.PointList;

import java.util.List;

/**
 *
 * @author Peter Karich
 */
public class EdgeMatch {

    private final EdgeIteratorState edgeState;
    private final List<GPXExtension> gpxExtensions;
    private final PointList wayGeometry;

    public EdgeMatch(EdgeIteratorState edgeState, List<GPXExtension> gpxExtension, PointList wayGeometry) {
        this.edgeState = edgeState;
        this.wayGeometry = wayGeometry;

        if (edgeState == null) {
            throw new IllegalStateException("Cannot fetch null EdgeState");
        }

        this.gpxExtensions = gpxExtension;
        if (this.gpxExtensions == null) {
            throw new IllegalStateException("extension list cannot be null");
        }
    }

    public EdgeMatch(EdgeIteratorState edgeState, List<GPXExtension> gpxExtension){
        this(edgeState, gpxExtension, null);
    }

    public boolean isEmpty() {
        return gpxExtensions.isEmpty();
    }

    public EdgeIteratorState getEdgeState() {
        return edgeState;
    }

    public List<GPXExtension> getGpxExtensions() {
        return gpxExtensions;
    }

    public double getMinDistance() {
        if (isEmpty()) {
            throw new IllegalStateException("No minimal distance for " + edgeState);
        }

        double min = Double.MAX_VALUE;
        for (GPXExtension gpxExt : gpxExtensions) {
            if (gpxExt.queryResult.getQueryDistance() < min) {
                min = gpxExt.queryResult.getQueryDistance();
            }
        }
        return min;
    }

    public PointList getWayGeometry() {
        if(wayGeometry != null)
            return wayGeometry;
        else
            return edgeState.fetchWayGeometry(0);
    }

    @Override
    public String toString() {
        return "edge:" + edgeState + ", extensions:" + gpxExtensions;
    }
}

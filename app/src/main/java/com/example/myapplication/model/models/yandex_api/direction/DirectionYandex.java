package com.example.myapplication.model.models.yandex_api.direction;

import com.squareup.moshi.Json;
import java.util.List;

public class DirectionYandex {
        @Json(name = "segments")
        private List<Segment> segments = null;

        public List<Segment> getSegments() {
            return segments;
        }

        public void setSegments(List<Segment> segments) {
            this.segments = segments;
        }
}

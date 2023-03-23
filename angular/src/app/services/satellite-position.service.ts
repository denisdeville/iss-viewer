import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { IssCoordinates } from '../models/iss-coordinates';

@Injectable({
  providedIn: 'root'
})
export class SatellitePositionService {

  private baseUrl = 'http://localhost:8080/iss/position'

  constructor(private httpClient: HttpClient) { }

  public getSatelliteInfos(id: number): Observable<IssCoordinates> {
    return this.httpClient.get<IssCoordinates>(`${this.baseUrl}/${id}`)
  }
}

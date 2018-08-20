import { HttpErrorResponse } from '@angular/common/http';
import { OtherService } from './../other.service';
import { ErrorResponse } from './../model/ErrorResponse';
import { MyResponse } from './../model/Response';
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-user',
  templateUrl: './user.component.html',
  styleUrls: ['./user.component.css']
})
export class UserComponent implements OnInit {
  public response: MyResponse;
  public errorResponse: String;
  public error: boolean;

  constructor(private otherService: OtherService) {
    this.response = new MyResponse();
    this.error = false;
  }

  ngOnInit() {
    this.otherService.requestUserResource().subscribe((r: MyResponse) => {
      this.response = r;
    }, (httpError: HttpErrorResponse) => {
      this.error = true;
      this.errorResponse = httpError.message;
    });
  }

}

